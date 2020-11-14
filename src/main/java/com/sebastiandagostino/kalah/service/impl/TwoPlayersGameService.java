package com.sebastiandagostino.kalah.service.impl;

import com.sebastiandagostino.kalah.domain.Board;
import com.sebastiandagostino.kalah.domain.PlayerType;
import com.sebastiandagostino.kalah.dto.BoardDTO;
import com.sebastiandagostino.kalah.dto.GameDTO;
import com.sebastiandagostino.kalah.dao.GameRepository;
import com.sebastiandagostino.kalah.dto.PlayerDTO;
import com.sebastiandagostino.kalah.exception.GameNotFoundException;
import com.sebastiandagostino.kalah.exception.GameOverException;
import com.sebastiandagostino.kalah.exception.IllegalPlayerTurnException;
import com.sebastiandagostino.kalah.service.GameOverVerificationService;
import com.sebastiandagostino.kalah.service.GameService;
import com.sebastiandagostino.kalah.service.TurnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwoPlayersGameService implements GameService {

    private static final Logger logger = LoggerFactory.getLogger(TwoPlayersGameService.class);

    private static final int DEFAULT_STARTING_SCORE = 0;

    private final Integer BOARD_SIZE;
    private final Integer AVAILABLE_STONES;

    private GameRepository gameRepository;
    private BoardMapperService boardMapperService;
    private TurnService turnService;
    private List<GameOverVerificationService> gameOverVerificationServices;

    public TwoPlayersGameService(@Value("${kalah.board.defaultSize}") Integer boardSize,
                                 @Value("${kalah.board.defaultStones}") Integer availableStones,
                                 @Autowired GameRepository gameRepository,
                                 @Autowired BoardMapperService boardMapperService,
                                 @Autowired TurnService turnService,
                                 @Autowired List<GameOverVerificationService> gameOverVerificationServices) {
        this.BOARD_SIZE = boardSize;
        this.AVAILABLE_STONES = availableStones;
        this.gameRepository = gameRepository;
        this.boardMapperService = boardMapperService;
        this.turnService = turnService;
        this.gameOverVerificationServices = gameOverVerificationServices;
    }

    @Override
    public GameDTO getGame(Long gameId) {
        return gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
    }

    @Override
    public GameDTO newGame() {
        GameDTO gameDTO = new GameDTO();
        Board board = new Board(BOARD_SIZE, AVAILABLE_STONES);
        BoardDTO boardDTO = boardMapperService.mapDTO(new BoardDTO(), board);
        gameDTO.setBoard(boardDTO);
        // Game starts with player 1 by default
        PlayerDTO player1 = new PlayerDTO(PlayerType.PLAYER_1, true, DEFAULT_STARTING_SCORE);
        gameDTO.setPlayer1(player1);
        PlayerDTO player2 = new PlayerDTO(PlayerType.PLAYER_2, false, DEFAULT_STARTING_SCORE);
        gameDTO.setPlayer2(player2);
        gameDTO.setGameOver(false);

        GameDTO saved = gameRepository.save(gameDTO);
        logger.info("Game #{} created", saved.getId());
        return saved;
    }

    @Override
    public GameDTO newMove(Long gameId, PlayerType playerType, Integer position) {
        GameDTO gameDTO = getGame(gameId);
        if (gameDTO.getGameOver()) {
            logger.error("In Game #{}, cannot play any move because game is already over", gameId);
            throw new GameOverException();
        }

        logger.info("In Game #{}, {} plays position #{}", gameId, playerType, position);
        if (!turnService.playsNext(gameDTO, playerType)) {
            logger.error("In Game #{}, player {} cannot play because it is not his/her turn", gameId, playerType);
            throw new IllegalPlayerTurnException();
        }

        Board board = boardMapperService.mapModel(gameDTO.getBoard());
        boolean takesAnotherTurn = board.move(playerType, position);

        // Update player score and turn
        gameDTO.getPlayer1().setScore(board.getPitCollectionForPlayer1().getScore());
        gameDTO.getPlayer2().setScore(board.getPitCollectionForPlayer2().getScore());
        if (!takesAnotherTurn) {
            turnService.updateTurn(gameDTO);
            logger.info("In Game #{}, turn has changed to the next player", gameId);
        } else {
            logger.info("In Game #{}, {} plays another turn", gameId, playerType);
        }

        boolean isGameOver = gameOverVerificationServices.parallelStream().anyMatch(e -> e.verifyGameOver(board));
        gameDTO.setGameOver(isGameOver);
        if (isGameOver) {
            // The player who still has pieces on his/her side of the board when the game ends captures remaining pieces
            board.captureAllStones();
            logger.info("In Game #{}, all stones have been captured after the game is over");
        } else {
            logger.info("Game #{} is not over after move", gameId);
        }

        BoardDTO boardDTO = boardMapperService.mapDTO(gameDTO.getBoard(), board);
        gameDTO.setBoard(boardDTO);

        GameDTO saved = gameRepository.save(gameDTO);
        logger.info("Game #{} updated", saved.getId());
        return saved;
    }

}

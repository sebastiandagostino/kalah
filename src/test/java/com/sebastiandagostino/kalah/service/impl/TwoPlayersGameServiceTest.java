package com.sebastiandagostino.kalah.service.impl;

import com.sebastiandagostino.kalah.dao.GameRepository;
import com.sebastiandagostino.kalah.domain.Board;
import com.sebastiandagostino.kalah.domain.PitCollection;
import com.sebastiandagostino.kalah.domain.PlayerType;
import com.sebastiandagostino.kalah.dto.BoardDTO;
import com.sebastiandagostino.kalah.dto.GameDTO;
import com.sebastiandagostino.kalah.dto.PlayerDTO;
import com.sebastiandagostino.kalah.exception.GameNotFoundException;
import com.sebastiandagostino.kalah.exception.GameOverException;
import com.sebastiandagostino.kalah.exception.IllegalPlayerTurnException;
import com.sebastiandagostino.kalah.service.GameOverVerificationService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class TwoPlayersGameServiceTest {

    private static final long ID = 1L;
    private static final PlayerType PLAYER = PlayerType.PLAYER_1;
    private static final int POSITION = 0;

    @InjectMocks
    private TwoPlayersGameService gameService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameDTO gameDTO, savedGameDTO;

    @Mock
    private BoardDTO boardDTO;

    @Mock
    private Board board;

    @Mock
    private PlayerDTO player1, player2;

    @Mock
    private PitCollection pitCollection1, pitCollection2;

    @Mock
    private TwoPlayersTurnService turnService;

    @Mock
    private BoardMapperService boardMapperService;

    @Mock
    private List<GameOverVerificationService> gameOverVerificationServices;

    @Mock
    private ScoreGameOverVerificationService scoreGameOverVerificationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetGameIsSuccessful() {
        when(gameRepository.findById(ID)).thenReturn(Optional.of(gameDTO));

        assertNotNull(gameService.getGame(ID));
    }

    @Test
    public void testGetGameFails() {
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> gameService.getGame(ID));
    }

    @Test
    public void testNewMoveFailsWhenIsNotPlayerTurn() {
        PlayerType playerType = PLAYER;
        when(gameRepository.findById(ID)).thenReturn(Optional.of(gameDTO));
        when(turnService.playsNext(gameDTO, playerType)).thenReturn(false);

        assertThrows(IllegalPlayerTurnException.class, () -> gameService.newMove(ID, playerType, 0));
    }

    @Test
    public void testNewMoveWithoutGameOverAndNoSkipTurn() {
        PlayerType playerType = PLAYER;
        when(gameRepository.findById(ID)).thenReturn(Optional.of(gameDTO));
        when(turnService.playsNext(gameDTO, playerType)).thenReturn(true);
        when(boardMapperService.mapModel(gameDTO.getBoard())).thenReturn(board);
        when(board.move(playerType, POSITION)).thenReturn(false); // Does not skip turn
        List<GameOverVerificationService> services = Lists.newArrayList(scoreGameOverVerificationService);
        when(gameOverVerificationServices.parallelStream()).thenReturn(services.parallelStream());
        when(scoreGameOverVerificationService.verifyGameOver(board)).thenReturn(false); // Game not over
        when(gameDTO.getPlayer1()).thenReturn(player1);
        when(gameDTO.getPlayer2()).thenReturn(player2);
        when(board.getPitCollectionForPlayer1()).thenReturn(pitCollection1);
        when(board.getPitCollectionForPlayer2()).thenReturn(pitCollection2);
        when(boardMapperService.mapDTO(gameDTO.getBoard(), board)).thenReturn(boardDTO);
        when(gameRepository.save(gameDTO)).thenReturn(savedGameDTO);

        GameDTO gameAfterMove = gameService.newMove(ID, playerType, POSITION);

        assertNotSame(gameAfterMove, gameDTO);
    }

    @Test
    public void testNewMoveWithoutGameOverAndSkipTurn() {
        PlayerType playerType = PLAYER;
        when(gameRepository.findById(ID)).thenReturn(Optional.of(gameDTO));
        when(turnService.playsNext(gameDTO, playerType)).thenReturn(true);
        when(boardMapperService.mapModel(gameDTO.getBoard())).thenReturn(board);
        when(board.move(playerType, POSITION)).thenReturn(true); // Skips turn
        List<GameOverVerificationService> services = Lists.newArrayList(scoreGameOverVerificationService);
        when(gameOverVerificationServices.parallelStream()).thenReturn(services.parallelStream());
        when(scoreGameOverVerificationService.verifyGameOver(board)).thenReturn(false); // Game not over
        when(gameDTO.getPlayer1()).thenReturn(player1);
        when(gameDTO.getPlayer2()).thenReturn(player2);
        when(board.getPitCollectionForPlayer1()).thenReturn(pitCollection1);
        when(board.getPitCollectionForPlayer2()).thenReturn(pitCollection2);
        when(boardMapperService.mapDTO(gameDTO.getBoard(), board)).thenReturn(boardDTO);
        when(gameRepository.save(gameDTO)).thenReturn(savedGameDTO);

        GameDTO gameAfterMove = gameService.newMove(ID, playerType, POSITION);

        assertNotSame(gameAfterMove, gameDTO);
    }

    @Test
    public void testNewMoveWithGameOver() {
        PlayerType playerType = PLAYER;
        when(gameRepository.findById(ID)).thenReturn(Optional.of(gameDTO));
        when(turnService.playsNext(gameDTO, playerType)).thenReturn(true);
        when(boardMapperService.mapModel(gameDTO.getBoard())).thenReturn(board);
        when(board.move(playerType, POSITION)).thenReturn(true); // Skips turn
        List<GameOverVerificationService> services = Lists.newArrayList(scoreGameOverVerificationService);
        when(gameOverVerificationServices.parallelStream()).thenReturn(services.parallelStream());
        when(scoreGameOverVerificationService.verifyGameOver(board)).thenReturn(true); // Game over
        when(gameDTO.getPlayer1()).thenReturn(player1);
        when(gameDTO.getPlayer2()).thenReturn(player2);
        when(board.getPitCollectionForPlayer1()).thenReturn(pitCollection1);
        when(board.getPitCollectionForPlayer2()).thenReturn(pitCollection2);
        when(boardMapperService.mapDTO(gameDTO.getBoard(), board)).thenReturn(boardDTO);
        when(gameRepository.save(gameDTO)).thenReturn(savedGameDTO);

        GameDTO gameAfterMove = gameService.newMove(ID, playerType, POSITION);

        assertNotSame(gameAfterMove, gameDTO);
    }


    @Test
    public void testNewMoveFailsWithGameAlreadyOver() {
        when(gameDTO.getGameOver()).thenReturn(true);
        when(gameRepository.findById(ID)).thenReturn(Optional.of(gameDTO));

        assertThrows(GameOverException.class, () -> gameService.newMove(ID, PLAYER, POSITION));
    }

}

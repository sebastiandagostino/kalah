package com.sebastiandagostino.kalah.controller;

import com.sebastiandagostino.kalah.domain.PlayerType;
import com.sebastiandagostino.kalah.dto.GameDTO;
import com.sebastiandagostino.kalah.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "v1/kalah")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/game/{gameId}")
    public @ResponseBody
    GameDTO getGame(@Valid @PathVariable Long gameId) {
        return gameService.getGame(gameId);
    }

    @PostMapping("/game")
    @ResponseStatus(HttpStatus.CREATED)
    public GameDTO newGame() {
        return gameService.newGame();
    }

    @PostMapping("/game/{gameId}/player/{playerType}/move/{position}")
    @ResponseStatus(HttpStatus.OK)
    public GameDTO newMove(@Valid @PathVariable Long gameId,
                     @Valid @PathVariable PlayerType playerType,
                     @Valid @PathVariable Integer position) {
        return gameService.newMove(gameId, playerType, position);
    }
}

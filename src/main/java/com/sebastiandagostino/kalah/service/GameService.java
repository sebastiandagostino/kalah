package com.sebastiandagostino.kalah.service;

import com.sebastiandagostino.kalah.domain.PlayerType;
import com.sebastiandagostino.kalah.dto.GameDTO;

public interface GameService {

    GameDTO getGame(Long gameId);

    GameDTO newGame();

    GameDTO newMove(Long gameId, PlayerType playerType, Integer position);
}

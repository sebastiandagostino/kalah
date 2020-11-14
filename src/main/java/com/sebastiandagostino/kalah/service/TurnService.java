package com.sebastiandagostino.kalah.service;

import com.sebastiandagostino.kalah.domain.PlayerType;
import com.sebastiandagostino.kalah.dto.GameDTO;

public interface TurnService {

    boolean playsNext(GameDTO gameDTO, PlayerType player);

    void updateTurn(GameDTO gameDTO);
}

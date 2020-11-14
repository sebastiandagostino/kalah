package com.sebastiandagostino.kalah.service.impl;

import com.sebastiandagostino.kalah.domain.PlayerType;
import com.sebastiandagostino.kalah.dto.GameDTO;
import com.sebastiandagostino.kalah.exception.ValidationException;
import com.sebastiandagostino.kalah.service.TurnService;
import org.springframework.stereotype.Service;

@Service
public class TwoPlayersTurnService implements TurnService {

    @Override
    public boolean playsNext(GameDTO gameDTO, PlayerType player) {
        if (gameDTO == null || player == null) {
            throw new ValidationException();
        }
        if (gameDTO.getPlayer1().getPlayerType().equals(player)) {
            return gameDTO.getPlayer1().getTurn();
        } else {
            return gameDTO.getPlayer2().getTurn();
        }
    }

    @Override
    public void updateTurn(GameDTO gameDTO) {
        if (gameDTO == null) {
            throw new ValidationException();
        }
        gameDTO.getPlayer1().setTurn(!gameDTO.getPlayer1().getTurn());
        gameDTO.getPlayer2().setTurn(!gameDTO.getPlayer2().getTurn());
    }
}

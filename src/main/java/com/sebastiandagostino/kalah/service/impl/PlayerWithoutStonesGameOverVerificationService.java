package com.sebastiandagostino.kalah.service.impl;

import com.sebastiandagostino.kalah.domain.Board;
import com.sebastiandagostino.kalah.exception.ValidationException;
import com.sebastiandagostino.kalah.service.GameOverVerificationService;
import org.springframework.stereotype.Service;

@Service
public class PlayerWithoutStonesGameOverVerificationService implements GameOverVerificationService {

    @Override
    public boolean verifyGameOver(Board board) {
        if (board == null || board.getPitCollectionForPlayer1().getPitCount()
                != board.getPitCollectionForPlayer2().getPitCount()) {
            throw new ValidationException();
        }
        int pitsPlayer1 = board.getPitCollectionForPlayer1().getPitCount() - 1;
        int sumPlayer1 = 0, sumPlayer2 = 0;
        for (int i = 0; i < pitsPlayer1; i++) {
            sumPlayer1 += board.getPitCollectionForPlayer1().getStonesAt(i);
            sumPlayer2 += board.getPitCollectionForPlayer2().getStonesAt(i);
        }
        return sumPlayer1 == 0 || sumPlayer2 == 0;
    }

}

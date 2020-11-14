package com.sebastiandagostino.kalah.service.impl;

import com.sebastiandagostino.kalah.domain.Board;
import com.sebastiandagostino.kalah.exception.ValidationException;
import com.sebastiandagostino.kalah.service.GameOverVerificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ScoreGameOverVerificationService implements GameOverVerificationService {

    // This is package private instead of private due to Mockito restrictions
    Integer scoreThreshold;

    public ScoreGameOverVerificationService(@Value("${kalah.game.scoreThreshold}") Integer scoreThreshold) {
        this.scoreThreshold = scoreThreshold;
    }

    private Integer getScoreThreshold() {
        return scoreThreshold;
    }

    @Override
    public boolean verifyGameOver(Board board) {
        if (board == null) {
            throw new ValidationException();
        }
        if (board.getPitCollectionForPlayer1().getScore() > getScoreThreshold()) {
            return true;
        }
        return board.getPitCollectionForPlayer2().getScore() > getScoreThreshold();
    }
}

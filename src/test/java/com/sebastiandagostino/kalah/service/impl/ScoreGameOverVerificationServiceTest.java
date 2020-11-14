package com.sebastiandagostino.kalah.service.impl;

import com.sebastiandagostino.kalah.domain.Board;
import com.sebastiandagostino.kalah.domain.PitCollection;
import com.sebastiandagostino.kalah.exception.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

public class ScoreGameOverVerificationServiceTest {

    private static final int SCORE_THRESHOLD = 24;
    private static final int SCORE_BELOW_THRESHOLD = 6;
    private static final int SCORE_ABOVE_THRESHOLD = 26;

    @InjectMocks
    private ScoreGameOverVerificationService gameOverVerificationService;

    @Mock
    private Board board;

    @Mock
    private PitCollection pitCollection1, pitCollection2;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        gameOverVerificationService.scoreThreshold = SCORE_THRESHOLD;
    }

    @Test
    public void testVerifyGameOverFailure() {
        assertThrows(ValidationException.class, () -> gameOverVerificationService.verifyGameOver(null));
    }

    @Test
    public void testVerifyGameOverNotAchievedByAnyPlayer() {
        mockBoard(SCORE_BELOW_THRESHOLD, SCORE_BELOW_THRESHOLD);

        assertFalse(gameOverVerificationService.verifyGameOver(board));
    }

    @Test
    public void testVerifyGameOverAchievedByPlayer1() {
        mockBoard(SCORE_ABOVE_THRESHOLD, SCORE_BELOW_THRESHOLD);

        assertTrue(gameOverVerificationService.verifyGameOver(board));
    }

    @Test
    public void testVerifyGameOverAchievedByPlayer2() {
        mockBoard(SCORE_BELOW_THRESHOLD, SCORE_ABOVE_THRESHOLD);

        assertTrue(gameOverVerificationService.verifyGameOver(board));
    }

    private void mockBoard(int scoreBelowThreshold, int scoreAboveThreshold) {
        when(board.getPitCollectionForPlayer1()).thenReturn(pitCollection1);
        when(board.getPitCollectionForPlayer2()).thenReturn(pitCollection2);
        when(pitCollection1.getScore()).thenReturn(scoreBelowThreshold);
        when(pitCollection2.getScore()).thenReturn(scoreAboveThreshold);
    }
}

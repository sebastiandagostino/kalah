package com.sebastiandagostino.kalah.service.impl;

import com.sebastiandagostino.kalah.domain.Board;
import com.sebastiandagostino.kalah.domain.PitCollection;
import com.sebastiandagostino.kalah.exception.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class PlayerWithoutStonesGameOverVerificationServiceTest {

    private static final int PIT_COUNT_1 = 6;
    private static final int PIT_COUNT_2 = 5;

    private static final int STONES_NOT_ZERO = 1;
    private static final int STONES_ZERO = 0;

    @InjectMocks
    private PlayerWithoutStonesGameOverVerificationService gameOverVerificationService;

    @Mock
    private Board board;

    @Mock
    private PitCollection pitCollection1, pitCollection2;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testVerifyGameOverFailureOnNullParameter() {
        assertThrows(ValidationException.class, () -> gameOverVerificationService.verifyGameOver(null));
    }

    @Test
    public void testVerifyGameOverFailureOnInconsistentBoard() {
        when(board.getPitCollectionForPlayer1()).thenReturn(pitCollection1);
        when(board.getPitCollectionForPlayer2()).thenReturn(pitCollection2);
        when(pitCollection1.getPitCount()).thenReturn(PIT_COUNT_1);
        when(pitCollection2.getPitCount()).thenReturn(PIT_COUNT_2);

        assertThrows(ValidationException.class, () -> gameOverVerificationService.verifyGameOver(board));
    }

    @Test
    public void testVerifyGameOverNotAchievedByAnyPlayer() {
        mockBoardWithValues(STONES_NOT_ZERO, STONES_NOT_ZERO);

        assertFalse(gameOverVerificationService.verifyGameOver(board));
    }

    @Test
    public void testVerifyGameOverAchievedByPlayer1() {
        mockBoardWithValues(STONES_ZERO, STONES_NOT_ZERO);

        assertTrue(gameOverVerificationService.verifyGameOver(board));
    }

    @Test
    public void testVerifyGameOverAchievedByPlayer2() {
        mockBoardWithValues(STONES_NOT_ZERO, STONES_ZERO);

        assertTrue(gameOverVerificationService.verifyGameOver(board));
    }

    private void mockBoardWithValues(int stonesNotZero, int stonesZero) {
        when(board.getPitCollectionForPlayer1()).thenReturn(pitCollection1);
        when(board.getPitCollectionForPlayer2()).thenReturn(pitCollection2);
        when(pitCollection1.getPitCount()).thenReturn(PIT_COUNT_1);
        when(pitCollection2.getPitCount()).thenReturn(PIT_COUNT_1);
        when(pitCollection1.getStonesAt(anyInt())).thenReturn(stonesNotZero);
        when(pitCollection2.getStonesAt(anyInt())).thenReturn(stonesZero);
    }
}

package com.sebastiandagostino.kalah.domain;

import com.sebastiandagostino.kalah.exception.IllegalPlayerMoveException;
import com.sebastiandagostino.kalah.exception.ValidationException;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertSame;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private static final int BOARD_SIZE = 6;
    private static final int AVAILABLE_STONES = 24;

    @Test
    public void testConstructionIsSuccessful() {
        Board board = new Board(BOARD_SIZE, AVAILABLE_STONES);

        final int division = AVAILABLE_STONES / BOARD_SIZE;
        for (int i = 0; i < BOARD_SIZE; i++) {
            assertSame(division, board.getPitCollectionForPlayer1().getStonesAt(i));
            assertSame(division, board.getPitCollectionForPlayer2().getStonesAt(i));
        }
        assertSame(0, board.getPitCollectionForPlayer1().getScore());
        assertSame(0, board.getPitCollectionForPlayer2().getScore());
        assertSame(BOARD_SIZE + 1, board.getPitCount());
        assertSame(BOARD_SIZE, board.getBoardSize());
    }

    @Test
    public void testConstructionFailsValidations() {
        assertThrows(ValidationException.class, () -> new Board(-1, -1));
    }

    @Test
    public void testConstructionFromDtoIsSuccessful() {
        List<Integer> list1 = Lists.newArrayList(5, 4, 4, 4, 4, 4, 0);
        List<Integer> list2 = Lists.newArrayList(4, 4, 4, 0, 5, 5, 1);
        Board board = new Board(list1, list2);

        for (int i = 0; i < list1.size(); i++) {
            assertSame(list1.get(i), board.getPitCollectionForPlayer1().getStonesAt(i));
            assertSame(list2.get(i), board.getPitCollectionForPlayer2().getStonesAt(i));
        }
        assertSame(list1.size(), board.getPitCount());
        assertSame(list1.size() - 1, board.getBoardSize());
    }

    @Test
    public void testConstructionFromDtoFailsValidationsFromNullPointer() {
        assertThrows(ValidationException.class, () -> new Board(null, null));
    }

    @Test
    public void testConstructionFromDtoFailsValidationsFromListsWithDifferentSizes() {
        assertThrows(ValidationException.class, () -> new Board(Lists.newArrayList(1, 2, 3), Lists.newArrayList(1, 2)));
    }

    @Test
    public void testConstructionFromDtoFailsValidationsFromShortLists() {
        assertThrows(ValidationException.class, () -> new Board(Lists.newArrayList(1), Lists.newArrayList(1)));
    }

    @Test
    public void testMoveIsSuccessfulWithoutContinuation() {
        Board board = new Board(BOARD_SIZE, AVAILABLE_STONES);

        boolean continues = board.move(PlayerType.PLAYER_1, 3);

        List<Integer> list2 = Lists.newArrayList(5, 4, 4, 4, 4, 4, 0);
        List<Integer> list1 = Lists.newArrayList(4, 4, 4, 0, 5, 5, 1);
        for (int i = 0; i < board.getBoardSize(); i++) {
            assertSame(list1.get(i), board.getPitCollectionForPlayer1().getStonesAt(i));
            assertSame(list2.get(i), board.getPitCollectionForPlayer2().getStonesAt(i));
        }
        assertFalse(continues);
    }

    @Test
    public void testMoveIsSuccessfulWithContinuation() {
        Board board = new Board(BOARD_SIZE, AVAILABLE_STONES);

        boolean continues = board.move(PlayerType.PLAYER_1, 2);

        List<Integer> list2 = Lists.newArrayList(4, 4, 4, 4, 4, 4, 0);
        List<Integer> list1 = Lists.newArrayList(4, 4, 0, 5, 5, 5, 1);
        for (int i = 0; i < board.getBoardSize(); i++) {
            assertSame(list1.get(i), board.getPitCollectionForPlayer1().getStonesAt(i));
            assertSame(list2.get(i), board.getPitCollectionForPlayer2().getStonesAt(i));
        }
        assertTrue(continues);
    }

    @Test
    public void testMoveFails() {
        List<Integer> list2 = Lists.newArrayList(5, 4, 4, 4, 4, 4, 0);
        List<Integer> list1 = Lists.newArrayList(4, 4, 4, 0, 5, 5, 1);
        Board board = new Board(list1, list2);

        assertThrows(IllegalPlayerMoveException.class, () -> board.move(PlayerType.PLAYER_1, 3));
    }

    @Test
    public void testCaptureAllStones() {
        List<Integer> list1 = Lists.newArrayList(5, 4, 4, 4, 4, 4, 0);
        List<Integer> list2 = Lists.newArrayList(4, 4, 4, 0, 5, 5, 1);
        Board board = new Board(list1, list2);

        board.captureAllStones();

        for (int i = 0; i < board.getBoardSize(); i++) {
            assertSame(0, board.getPitCollectionForPlayer1().getStonesAt(i));
            assertSame(0, board.getPitCollectionForPlayer2().getStonesAt(i));
        }
        int sum1 = list1.stream().mapToInt(i -> i).sum();
        int sum2 = list2.stream().mapToInt(i -> i).sum();
        assertSame(sum1, board.getPitCollectionForPlayer1().getScore());
        assertSame(sum2, board.getPitCollectionForPlayer2().getScore());
    }

}

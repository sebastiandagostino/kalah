package com.sebastiandagostino.kalah.domain;

import com.sebastiandagostino.kalah.exception.ValidationException;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PitCollectionTest {

    private static final int BOARD_SIZE = 6;
    private static final int AVAILABLE_STONES = 24;

    @Test
    public void testConstructionIsSuccessful() {
        PitCollection pitCollection = new PitCollection(BOARD_SIZE, AVAILABLE_STONES);

        final int division = AVAILABLE_STONES / BOARD_SIZE;
        for (int i = 0; i < BOARD_SIZE; i++) {
            assertSame(division, pitCollection.getStonesAt(i));
        }
        assertSame(0, pitCollection.getStonesAt(BOARD_SIZE));
        assertSame(BOARD_SIZE + 1, pitCollection.getPitCount());
        assertSame(BOARD_SIZE, pitCollection.getBoardSize());
        assertSame(0, pitCollection.getScore());
    }

    @Test
    public void testConstructionFailsValidations() {
        assertThrows(ValidationException.class, () -> new PitCollection(-1, -1));
    }

    @Test
    public void testConstructionFromDtoIsSuccessful() {
        List<Integer> list = Lists.newArrayList(4, 4, 4, 4, 4, 4, 0);
        PitCollection pitCollection = new PitCollection(list);

        for (int i = 0; i < list.size(); i++) {
            assertSame(list.get(i), pitCollection.getStonesAt(i));
        }
        assertSame(list.size(), pitCollection.getPitCount());
        assertSame(list.size() - 1, pitCollection.getBoardSize());
    }

    @Test
    public void testConstructionFromDtoFailsValidationsFromNullPointer() {
        assertThrows(ValidationException.class, () -> new PitCollection(null));
    }

    @Test
    public void testConstructionFromDtoFailsValidationsFromShortList() {
        assertThrows(ValidationException.class, () -> new PitCollection(Lists.newArrayList(1)));
    }

    @Test
    public void testMutuallyLinkWith() {
        PitCollection pitCollection1 = new PitCollection(BOARD_SIZE, AVAILABLE_STONES);
        PitCollection pitCollection2 = new PitCollection(BOARD_SIZE, AVAILABLE_STONES);

        pitCollection1.mutuallyLinkWith(pitCollection2);
        pitCollection1.move(3);

        assertSame(4, pitCollection1.getStonesAt(0));
        assertSame(4, pitCollection1.getStonesAt(1));
        assertSame(4, pitCollection1.getStonesAt(2));
        assertSame(0, pitCollection1.getStonesAt(3));
        assertSame(5, pitCollection1.getStonesAt(4));
        assertSame(5, pitCollection1.getStonesAt(5));
        assertSame(1, pitCollection1.getStonesAt(6));
        assertSame(1, pitCollection1.getScore());
        assertSame(5, pitCollection2.getStonesAt(0));
        assertSame(4, pitCollection2.getStonesAt(1));
        assertSame(4, pitCollection2.getStonesAt(2));
        assertSame(4, pitCollection2.getStonesAt(3));
        assertSame(4, pitCollection2.getStonesAt(4));
        assertSame(4, pitCollection2.getStonesAt(5));
        assertSame(0, pitCollection2.getStonesAt(6));
        assertSame(0, pitCollection2.getScore());
    }

    @Test
    public void testCapture() {
        PitCollection pitCollection = new PitCollection(BOARD_SIZE, AVAILABLE_STONES);

        pitCollection.capture(0);

        assertSame(0, pitCollection.getStonesAt(0));
        assertSame(4, pitCollection.getScore());
    }

    @Test
    public void testFailToGetStoneAtIllegalPosition() {
        PitCollection pitCollection = new PitCollection(BOARD_SIZE, AVAILABLE_STONES);

        assertThrows(ValidationException.class, () -> pitCollection.getStonesAt(9));
    }
}

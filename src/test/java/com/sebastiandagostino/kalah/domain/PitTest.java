package com.sebastiandagostino.kalah.domain;

import com.sebastiandagostino.kalah.exception.IllegalPlayerMoveException;
import com.sebastiandagostino.kalah.exception.ValidationException;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PitTest {

    @Test
    public void testCannotConstructWithNegativeStones() {
        assertThrows(ValidationException.class, () -> new Pit(-1));
    }

    @Test
    public void testFailedMove() {
        assertThrows(IllegalPlayerMoveException.class, () -> (new Pit(7)).move());
    }

    @Test
    public void testSuccessfulMove() {
        Pit pit1 = new Pit(5);
        Pit pit2 = new Pit(0);
        Pit pit3 = new Pit(0);
        StoragePit pit4 = new StoragePit();
        pit1.setNext(pit2);
        pit2.setNext(pit3);
        pit3.setNext(pit4);
        pit4.setNext(pit1);

        pit1.move();

        assertSame(1, pit1.getStones());
        assertSame(2, pit2.getStones());
        assertSame(1, pit3.getStones());
        assertSame(1, pit4.getStones());
    }

    @Test
    public void testEmpty() {
        int stones = 5;
        Pit pit = new Pit(stones);

        int emptied = pit.empty();

        assertSame(emptied, stones);
        assertSame(0, pit.getStones());
    }

}

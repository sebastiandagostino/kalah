package com.sebastiandagostino.kalah.domain;

import com.sebastiandagostino.kalah.exception.IllegalGameActionException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StoragePitTest {

    @InjectMocks
    private StoragePit storagePit;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMove() {
        assertThrows(IllegalGameActionException.class, () -> storagePit.move());
    }

    @Test
    public void testCaptureIsSuccessful() {
        int stones = storagePit.getStones();
        int captured = 5;

        storagePit.capture(captured);

        assertSame(captured + stones, storagePit.getStones());
    }

    @Test
    public void testCaptureFailsOnNegativeStones() {
        assertThrows(IllegalGameActionException.class, () -> storagePit.capture(-1));
    }

}

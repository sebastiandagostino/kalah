package com.sebastiandagostino.kalah.domain;

import com.sebastiandagostino.kalah.exception.IllegalGameActionException;

public class StoragePit extends Pit {

    private static final int DEFAULT_STORAGE_PIT_STONES = 0;

    public StoragePit() {
        super(DEFAULT_STORAGE_PIT_STONES);
    }

    public StoragePit(int stones) {
        super(stones);
    }

    public void capture(int stones) {
        if (stones < 0) {
            throw new IllegalGameActionException();
        }
        setStones(getStones() + stones);
    }

    @Override
    public void move() {
        throw new IllegalGameActionException();
    }
}

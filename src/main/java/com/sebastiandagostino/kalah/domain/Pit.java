package com.sebastiandagostino.kalah.domain;

import com.sebastiandagostino.kalah.exception.IllegalPlayerMoveException;
import com.sebastiandagostino.kalah.exception.ValidationException;

public class Pit {

    private Pit next;

    private int stones;

    public Pit(int stones) {
        if (stones < 0) {
            throw new ValidationException();
        }
        this.stones = stones;
    }

    public Pit getNext() {
        return next;
    }

    public void setNext(Pit next) {
        this.next = next;
    }

    public int getStones() {
        return stones;
    }

    protected void setStones(int stones) {
        this.stones = stones;
    }

    private void increaseStones() {
        stones += 1;
    }

    public int empty() {
        int removedStones = stones;
        stones = 0;
        return removedStones;
    }

    public void move() {
        int stones = empty();
        Pit next = this.getNext();
        while (stones > 0) {
            if (next == null) {
                throw new IllegalPlayerMoveException();
            }
            next.increaseStones();
            stones--;
            next = next.getNext();
        }
    }
}

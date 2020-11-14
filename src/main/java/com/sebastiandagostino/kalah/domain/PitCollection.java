package com.sebastiandagostino.kalah.domain;

import com.sebastiandagostino.kalah.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class PitCollection {

    private List<Pit> pitList;

    private StoragePit storagePit;

    public PitCollection(int boardSize, int availableStones) {
        if (boardSize <= 0 || availableStones <= 0 || boardSize > availableStones) {
            throw new ValidationException();
        }
        final int stones = availableStones / boardSize;
        pitList = new ArrayList<>();
        // Available stones are only distributed among pits that are not storage pit
        Pit previous = null;
        for (int i = 0; i < boardSize; i++) {
            Pit regularPit = new Pit(stones);
            if (previous != null) {
                previous.setNext(regularPit);
            }
            previous = regularPit;
            pitList.add(regularPit);
        }
        // StoragePit is always empty on construction
        storagePit = new StoragePit();
        previous.setNext(storagePit);
        pitList.add(storagePit);
    }

    public PitCollection(List<Integer> positionsForPlayer) {
        if (positionsForPlayer == null || positionsForPlayer.size() < 2) {
            throw new ValidationException();
        }
        pitList = new ArrayList<>();
        int boardSize = positionsForPlayer.size() - 1;
        Pit previous = null;
        for (int i = 0; i < boardSize; i++) {
            Pit regularPit = new Pit(positionsForPlayer.get(i));
            if (previous != null) {
                previous.setNext(regularPit);
            }
            previous = regularPit;
            pitList.add(regularPit);
        }
        // StoragePit in this case can have stones
        storagePit = new StoragePit(positionsForPlayer.get(boardSize));
        previous.setNext(storagePit);
        pitList.add(storagePit);
    }

    public void mutuallyLinkWith(PitCollection pitCollection) {
        if (pitCollection != null) {
            pitCollection.storagePit.setNext(pitList.stream().findFirst().orElse(null));
            storagePit.setNext(pitCollection.pitList.stream().findFirst().orElse(null));
        }
    }

    public int getStonesAt(int position) {
        if (position < 0 || position > pitList.size()) {
            throw new ValidationException();
        }
        return pitList.get(position).getStones();
    }

    public int getPitCount() {
        return pitList.size();
    }

    public int getBoardSize() {
        return getPitCount() - 1;
    }

    public int getScore() {
        return storagePit.getStones();
    }

    public void move(int position) {
        pitList.get(position).move();
    }

    public void capture(int position) {
        int stones = pitList.get(position).empty();
        storagePit.capture(stones);
    }
}

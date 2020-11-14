package com.sebastiandagostino.kalah.domain;

import com.sebastiandagostino.kalah.exception.IllegalPlayerMoveException;
import com.sebastiandagostino.kalah.exception.ValidationException;

import java.util.HashMap;
import java.util.List;

public class Board {

    private HashMap<PlayerType, PitCollection> pitCollectionForPlayer;

    private PitCollection pitCollectionForPlayer1;

    private PitCollection pitCollectionForPlayer2;

    public Board(int boardSize, int availableStones) {
        pitCollectionForPlayer1 = new PitCollection(boardSize, availableStones);
        pitCollectionForPlayer2 = new PitCollection(boardSize, availableStones);
        pitCollectionForPlayer1.mutuallyLinkWith(pitCollectionForPlayer2);
        createPitCollectionMap(pitCollectionForPlayer1, pitCollectionForPlayer2);
    }

    public Board(List<Integer> positionsForPlayer1, List<Integer> positionsForPlayer2) {
        if (positionsForPlayer1 == null || positionsForPlayer2 == null
                || positionsForPlayer1.size() != positionsForPlayer2.size()
                || positionsForPlayer1.size() < 2) {
            throw new ValidationException();
        }
        pitCollectionForPlayer1 = new PitCollection(positionsForPlayer1);
        pitCollectionForPlayer2 = new PitCollection(positionsForPlayer2);
        pitCollectionForPlayer1.mutuallyLinkWith(pitCollectionForPlayer2);
        createPitCollectionMap(pitCollectionForPlayer1, pitCollectionForPlayer2);
    }

    private void createPitCollectionMap(PitCollection pitCollectionForPlayer1, PitCollection pitCollectionForPlayer2) {
        pitCollectionForPlayer = new HashMap<>();
        pitCollectionForPlayer.put(PlayerType.PLAYER_1, pitCollectionForPlayer1);
        pitCollectionForPlayer.put(PlayerType.PLAYER_2, pitCollectionForPlayer2);
    }

    public PitCollection getPitCollectionForPlayer1() {
        return pitCollectionForPlayer1;
    }

    public PitCollection getPitCollectionForPlayer2() {
        return pitCollectionForPlayer2;
    }

    public int getBoardSize() {
        return pitCollectionForPlayer1.getBoardSize();
    }

    public int getPitCount() {
        return pitCollectionForPlayer1.getPitCount();
    }

    public boolean move(PlayerType playerType, int position) {
        if (!pitCollectionForPlayer.containsKey(playerType)) {
            throw new ValidationException();
        }
        int stones = pitCollectionForPlayer.get(playerType).getStonesAt(position);
        // Throws Exception if the player wants to move from a position with zero stones
        if (stones == 0) {
            throw new IllegalPlayerMoveException();
        }
        int distanceToStoragePit = getBoardSize() - position;
        pitCollectionForPlayer.get(playerType).move(position);
        // If the last piece you drop is in your own StoragePit, you take another turn
        return distanceToStoragePit == stones;
    }

    public void captureAllStones() {
        final int size = getBoardSize();
        for (int i = 0; i < size; i++) {
            pitCollectionForPlayer1.capture(i);
            pitCollectionForPlayer2.capture(i);
        }
    }
}

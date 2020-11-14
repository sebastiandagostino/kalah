package com.sebastiandagostino.kalah.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sebastiandagostino.kalah.domain.PlayerType;

import javax.persistence.*;

@Entity(name = "PLAYER")
public class PlayerDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    private PlayerType playerType;

    private Boolean turn;

    private int score;

    public PlayerDTO() {
    }

    public PlayerDTO(PlayerType playerType, boolean turn, int score) {
        this.playerType = playerType;
        this.turn = turn;
        this.score = score;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Boolean getTurn() {
        return turn;
    }

    public void setTurn(Boolean turn) {
        this.turn = turn;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

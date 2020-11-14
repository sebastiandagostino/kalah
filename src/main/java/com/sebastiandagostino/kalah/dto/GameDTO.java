package com.sebastiandagostino.kalah.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "GAME")
public class GameDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BOARD_ID")
    private BoardDTO board;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PLAYER_1_ID")
    private PlayerDTO player1;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PLAYER_2_ID")
    private PlayerDTO player2;

    private Boolean isGameOver;

    public GameDTO() {
    }

    public BoardDTO getBoard() {
        return board;
    }

    public void setBoard(BoardDTO board) {
        this.board = board;
    }

    public PlayerDTO getPlayer1() {
        return player1;
    }

    public void setPlayer1(PlayerDTO player1) {
        this.player1 = player1;
    }

    public PlayerDTO getPlayer2() {
        return player2;
    }

    public void setPlayer2(PlayerDTO player2) {
        this.player2 = player2;
    }

    public Boolean getGameOver() {
        return isGameOver;
    }

    public void setGameOver(Boolean gameOver) {
        isGameOver = gameOver;
    }
}

package com.sebastiandagostino.kalah.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "BOARD")
public class BoardDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "STONES")
    @ElementCollection()
    private List<Integer> pitCollectionForPlayer1;

    @Column(name = "STONES")
    @ElementCollection()
    private List<Integer> pitCollectionForPlayer2;

    public BoardDTO() {
    }

    public List<Integer> getPitCollectionForPlayer1() {
        return pitCollectionForPlayer1;
    }

    public void setPitCollectionForPlayer1(List<Integer> pitCollectionForPlayer1) {
        this.pitCollectionForPlayer1 = pitCollectionForPlayer1;
    }

    public List<Integer> getPitCollectionForPlayer2() {
        return pitCollectionForPlayer2;
    }

    public void setPitCollectionForPlayer2(List<Integer> pitCollectionForPlayer2) {
        this.pitCollectionForPlayer2 = pitCollectionForPlayer2;
    }
}

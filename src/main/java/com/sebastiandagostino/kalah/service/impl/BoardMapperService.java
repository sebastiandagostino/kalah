package com.sebastiandagostino.kalah.service.impl;

import com.sebastiandagostino.kalah.domain.Board;
import com.sebastiandagostino.kalah.dto.BoardDTO;
import com.sebastiandagostino.kalah.exception.ValidationException;
import com.sebastiandagostino.kalah.service.MapperService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BoardMapperService implements MapperService<Board, BoardDTO> {

    @Override
    public BoardDTO mapDTO(BoardDTO boardDTO, Board board) {
        if (boardDTO == null || board == null) {
            throw new ValidationException();
        }
        boardDTO.setPitCollectionForPlayer1(new ArrayList<>());
        boardDTO.setPitCollectionForPlayer2(new ArrayList<>());
        int count = board.getPitCount();
        for (int i = 0; i < count; i++) {
            boardDTO.getPitCollectionForPlayer1().add(board.getPitCollectionForPlayer1().getStonesAt(i));
            boardDTO.getPitCollectionForPlayer2().add(board.getPitCollectionForPlayer2().getStonesAt(i));
        }
        return boardDTO;
    }

    @Override
    public Board mapModel(BoardDTO boardDTO) {
        if (boardDTO == null) {
            throw new ValidationException();
        }
        return new Board(boardDTO.getPitCollectionForPlayer1(), boardDTO.getPitCollectionForPlayer2());
    }
}

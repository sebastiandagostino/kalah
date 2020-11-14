package com.sebastiandagostino.kalah.service.impl;

import com.sebastiandagostino.kalah.domain.Board;
import com.sebastiandagostino.kalah.domain.PitCollection;
import com.sebastiandagostino.kalah.dto.BoardDTO;
import com.sebastiandagostino.kalah.exception.ValidationException;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class BoardMapperServiceTest {

    private static final long ID = 1L;
    private static final int PIT_COUNT = 5;
    private static final int PIT_VALUE = 1;

    @InjectMocks
    private BoardMapperService boardMapperService;

    @Mock
    private Board board;

    @Mock
    private BoardDTO boardDTO;

    @Mock
    private PitCollection pitCollection;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMapDtoIsSuccessful() {
        when(boardDTO.getId()).thenReturn(ID);
        when(board.getPitCount()).thenReturn(PIT_COUNT);
        when(board.getPitCollectionForPlayer1()).thenReturn(pitCollection);
        when(board.getPitCollectionForPlayer2()).thenReturn(pitCollection);
        when(pitCollection.getStonesAt(anyInt())).thenReturn(PIT_VALUE);

        BoardDTO mapped = boardMapperService.mapDTO(boardDTO, board);

        assertSame(ID, mapped.getId());
        for (int i = 0; i < board.getBoardSize(); i++) {
            assertSame(board.getPitCollectionForPlayer1().getStonesAt(i), mapped.getPitCollectionForPlayer1().get(i));
            assertSame(board.getPitCollectionForPlayer2().getStonesAt(i), mapped.getPitCollectionForPlayer2().get(i));
        }
    }

    @Test
    public void testMapDtoFailsOnNullBoard() {
        assertThrows(ValidationException.class, () -> boardMapperService.mapDTO(boardDTO, null));
    }

    @Test
    public void testMapDtoFailsOnNullBoardDto() {
        assertThrows(ValidationException.class, () -> boardMapperService.mapDTO(null, board));
    }

    @Test
    public void testMapModelIsSuccessful() {
        when(boardDTO.getId()).thenReturn(ID);
        when(boardDTO.getPitCollectionForPlayer1()).thenReturn(Lists.newArrayList(1, 2, 3));
        when(boardDTO.getPitCollectionForPlayer2()).thenReturn(Lists.newArrayList(1, 2, 3));
        assertNotNull(boardMapperService.mapModel(boardDTO));
    }

    @Test
    public void testMapModelFailsOnNullBoardDto() {
        assertThrows(ValidationException.class, () -> boardMapperService.mapModel(null));
    }
}

package com.sebastiandagostino.kalah.service.impl;

import com.sebastiandagostino.kalah.domain.PlayerType;
import com.sebastiandagostino.kalah.dto.GameDTO;
import com.sebastiandagostino.kalah.dto.PlayerDTO;
import com.sebastiandagostino.kalah.exception.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class TwoPlayersTurnServiceTest {

    @InjectMocks
    private TwoPlayersTurnService twoPlayersTurnService;

    @Mock
    private GameDTO gameDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPlaysNextIsSuccessful() {
        PlayerDTO player1 = new PlayerDTO();
        player1.setTurn(true);
        player1.setPlayerType(PlayerType.PLAYER_1);
        when(gameDTO.getPlayer1()).thenReturn(player1);
        PlayerDTO player2 = new PlayerDTO();
        player2.setTurn(false);
        player2.setPlayerType(PlayerType.PLAYER_2);
        when(gameDTO.getPlayer2()).thenReturn(player2);

        assertTrue(twoPlayersTurnService.playsNext(gameDTO, PlayerType.PLAYER_1));
        assertFalse(twoPlayersTurnService.playsNext(gameDTO, PlayerType.PLAYER_2));
    }

    @Test
    public void testPlaysNextFailureOnNullGame() {
        assertThrows(ValidationException.class, () -> twoPlayersTurnService.playsNext(null, PlayerType.PLAYER_1));
    }

    @Test
    public void testPlaysNextFailureOnNullPlayer() {
        assertThrows(ValidationException.class, () -> twoPlayersTurnService.playsNext(gameDTO, null));
    }

    @Test
    public void testUpdateTurnIsSuccessful() {
        PlayerDTO player1 = new PlayerDTO();
        player1.setTurn(true);
        when(gameDTO.getPlayer1()).thenReturn(player1);
        PlayerDTO player2 = new PlayerDTO();
        player2.setTurn(false);
        when(gameDTO.getPlayer2()).thenReturn(player2);

        twoPlayersTurnService.updateTurn(gameDTO);

        assertFalse(player1.getTurn());
        assertTrue(player2.getTurn());
    }

    @Test
    public void testUpdateTurnFailureOnNullGame() {
        assertThrows(ValidationException.class, () -> twoPlayersTurnService.updateTurn(null));
    }

}

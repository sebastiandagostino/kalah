package com.sebastiandagostino.kalah.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Player is not allowed to play in the current turn")
public class IllegalPlayerTurnException extends ApiException {
}

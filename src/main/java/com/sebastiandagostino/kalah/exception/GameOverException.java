package com.sebastiandagostino.kalah.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Game is over")
public class GameOverException extends ApiException {
}

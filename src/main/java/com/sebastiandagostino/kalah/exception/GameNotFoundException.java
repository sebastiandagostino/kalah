package com.sebastiandagostino.kalah.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Game was not found")
public class GameNotFoundException extends ApiException {
}

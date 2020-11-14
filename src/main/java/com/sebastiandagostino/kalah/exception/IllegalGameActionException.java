package com.sebastiandagostino.kalah.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal action according to game rules")
public class IllegalGameActionException extends ApiException {
}

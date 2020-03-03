package com.pokemonshakespeare.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "In production code there would be an actual handler for this ;)")
public class CustomException extends RuntimeException {

}

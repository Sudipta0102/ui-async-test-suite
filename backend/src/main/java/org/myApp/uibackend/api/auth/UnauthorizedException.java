package org.myApp.uibackend.api.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception mapped to HTTP 401.
 *
 * Allows clean failure signaling to UI
 * without leaking any security details.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException{
}
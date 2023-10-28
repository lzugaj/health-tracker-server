package com.luv2code.health.tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.zalando.problem.Status.UNAUTHORIZED;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RefreshTokenException extends HealthTrackerException {

    public RefreshTokenException(String message) {
        super(UNAUTHORIZED, message, null);
    }
}

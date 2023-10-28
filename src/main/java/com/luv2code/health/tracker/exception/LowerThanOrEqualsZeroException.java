package com.luv2code.health.tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.zalando.problem.Status.BAD_REQUEST;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LowerThanOrEqualsZeroException extends HealthTrackerException {

    public LowerThanOrEqualsZeroException(String message) {
        super(BAD_REQUEST, message, null);
    }
}

package com.luv2code.health.tracker.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.StatusType;

import java.net.URI;
import java.util.Map;

public class HealthTrackerException extends AbstractThrowableProblem {

    public HealthTrackerException(
            StatusType statusType,
            String message,
            HttpServletRequest httpServletRequest,
            Map<String, Object> parameters
    ) {
        super(
                URI.create(httpServletRequest.getRequestURI()),
                statusType.getReasonPhrase(),
                statusType,
                message,
                null,
                null,
                parameters
        );
    }
    public HealthTrackerException(
            StatusType statusType,
            String message,
            Map<String, Object> parameters
    ) {
        super(
                null,
                null,
                statusType,
                message,
                null,
                null,
                parameters
        );
    }
}

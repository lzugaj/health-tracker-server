package com.luv2code.health.tracker.rest.handler;

import com.luv2code.health.tracker.exception.HealthTrackerException;
import com.luv2code.health.tracker.exception.LowerThanOrEqualsZeroException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.zalando.problem.Status.BAD_REQUEST;
import static org.zalando.problem.Status.NOT_FOUND;

@RestControllerAdvice
public class ResponseEntityExceptionHandler implements ProblemHandling {

    private static final String LOCAL_DATE_TIME_PARAMETER = "localDateTime";

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Problem> handleEntityNotFoundException(RuntimeException exception, HttpServletRequest httpServletRequest) {
        return createResponseMessage(NOT_FOUND, exception, httpServletRequest);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Problem> handleRuntimeException(RuntimeException exception, HttpServletRequest httpServletRequest) {
        return createResponseMessage(BAD_REQUEST, exception, httpServletRequest);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Problem> handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest httpServletRequest) {
        return createResponseMessage(BAD_REQUEST, exception, httpServletRequest);
    }

    @ExceptionHandler(value = LowerThanOrEqualsZeroException.class)
    public ResponseEntity<Problem> handleLowerThanOrEqualsZeroException(LowerThanOrEqualsZeroException exception, HttpServletRequest httpServletRequest) {
        return createResponseMessage(BAD_REQUEST, exception, httpServletRequest);
    }

    public ResponseEntity<Problem> createResponseMessage(StatusType statusType, Exception exception, HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put(LOCAL_DATE_TIME_PARAMETER, now());

        return ResponseEntity.status(statusType.getStatusCode())
                .body(new HealthTrackerException(statusType,exception,httpServletRequest, map));
    }
}

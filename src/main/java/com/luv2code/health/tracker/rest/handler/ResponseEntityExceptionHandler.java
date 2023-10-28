package com.luv2code.health.tracker.rest.handler;

import com.luv2code.health.tracker.exception.HealthTrackerException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.zalando.problem.Status.*;

@RestControllerAdvice
public class ResponseEntityExceptionHandler implements ProblemHandling {

    private static final String LOCAL_DATE_TIME_PARAMETER = "localDateTime";

    @ExceptionHandler(value = HealthTrackerException.class)
    public ResponseEntity<Problem> handleHealthTrackerException(HealthTrackerException exception, HttpServletRequest httpServletRequest) {
        return createResponseMessage(exception.getStatus(), exception, httpServletRequest);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Problem> handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest httpServletRequest) {
        return createResponseMessage(FORBIDDEN, exception, httpServletRequest);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<Problem> handleInsufficientAuthenticationExceptionException(InsufficientAuthenticationException exception, HttpServletRequest httpServletRequest) {
        return createResponseMessage(FORBIDDEN, exception, httpServletRequest);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Problem> handleEntityNotFoundException(EntityNotFoundException exception, HttpServletRequest httpServletRequest) {
        return createResponseMessage(NOT_FOUND, exception, httpServletRequest);
    }

    public ResponseEntity<Problem> createResponseMessage(StatusType statusType, Exception exception, HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put(LOCAL_DATE_TIME_PARAMETER, now());

        return ResponseEntity.status(statusType.getStatusCode())
                .body(new HealthTrackerException(
                        statusType,
                        exception.getMessage(),
                        httpServletRequest,
                        map)
                );
    }
}

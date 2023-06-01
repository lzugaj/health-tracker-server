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

@RestControllerAdvice
public class ResponseEntityExceptionHandler implements ProblemHandling {

    private static final String LOCAL_DATE_TIME_PARAMETER = "localDateTime";

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Problem> handleEntityNotFoundException(RuntimeException exception, HttpServletRequest httpServletRequest) {
        StatusType notFoundRequest = Status.NOT_FOUND;
        return createResponseMessage(notFoundRequest, exception, httpServletRequest);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Problem> handleRuntimeException(RuntimeException exception, HttpServletRequest httpServletRequest) {
        StatusType badRequest = Status.BAD_REQUEST;
        return createResponseMessage(badRequest, exception, httpServletRequest);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Problem> handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest httpServletRequest) {
        StatusType notFoundRequest = Status.BAD_REQUEST;
        return createResponseMessage(notFoundRequest, exception, httpServletRequest);
    }

    @ExceptionHandler(value = LowerThanOrEqualsZeroException.class)
    public ResponseEntity<Problem> handleLowerThanOrEqualsZeroException(LowerThanOrEqualsZeroException exception, HttpServletRequest httpServletRequest) {
        StatusType badRequest = Status.BAD_REQUEST;
        return createResponseMessage(badRequest, exception, httpServletRequest);
    }

    public ResponseEntity<Problem> createResponseMessage(StatusType statusType, Exception exception, HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put(LOCAL_DATE_TIME_PARAMETER, now());

        return ResponseEntity.status(statusType.getStatusCode())
                .body(new HealthTrackerException(statusType,exception,httpServletRequest, map));
    }
}

package com.luv2code.health.tracker.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.valueOf;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes;
import static org.springframework.web.context.request.RequestContextHolder.getRequestAttributes;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controller() {}

    @Before("controller()")
    public void logRequestDetails(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object[] methodArgs = joinPoint.getArgs();
        String methodArgsJson = getMethodArgsAsJson(methodArgs);

        Authentication authentication = getContext().getAuthentication();
        String userName = (authentication != null) ? authentication.getName() : "Anonymous";

        String methodName = joinPoint.getSignature().getName();

        log.info("Request - Method: {}, Path: {}, Server: {}, User: {}, Method Name: {}, Body: {}, Headers: {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getServerName(),
                userName,
                methodName,
                methodArgsJson,
                getHeadersAsJson(request)
        );
    }

    private String getMethodArgsAsJson(Object[] methodArgs) {
        try {
            return objectMapper.writeValueAsString(methodArgs);
        } catch (Exception e) {
            log.warn("Error converting methodArgs to JSON: {}", e.getMessage());
            return "[]";
        }
    }

    @AfterReturning(pointcut = "controller()", returning = "result")
    public void logResponse(Object result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) getRequestAttributes();
        HttpServletResponse response = requireNonNull(attributes).getResponse();

        HttpStatus status = INTERNAL_SERVER_ERROR;
        if (response != null) {
            status = valueOf(response.getStatus());
        }

        long responseTime = calculateResponseTime(attributes);

        log.info("Response - Status: {}, Body: {}, Response Time: {} ms",
                status,
                getResponseBodyAsJson(result),
                responseTime
        );
    }

    private String getResponseBodyAsJson(Object responseBody) {
        try {
            return objectMapper.writeValueAsString(responseBody);
        } catch (Exception e) {
            log.warn("Error converting responseBody to JSON: {}", e.getMessage());
            return "{}";
        }
    }

    private long calculateResponseTime(ServletRequestAttributes attributes) {
        StopWatch stopWatch = (StopWatch) attributes.getAttribute("stopWatch", SCOPE_REQUEST);
        return (stopWatch != null) ? stopWatch.getTotalTimeMillis() : -1;
    }

    private String getHeadersAsJson(HttpServletRequest request) {
        Map<String, String> headersMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headersMap.put(headerName, headerValue);
        }

        return convertMapToJson(headersMap);
    }

    private String convertMapToJson(Map<String, String> map) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            log.warn("Error converting Map to JSON: {}", e.getMessage());
            return "{}";
        }
    }
}


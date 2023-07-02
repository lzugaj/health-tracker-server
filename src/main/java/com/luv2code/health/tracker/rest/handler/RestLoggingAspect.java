package com.luv2code.health.tracker.rest.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.Collectors;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;
import static org.springframework.web.context.request.RequestContextHolder.getRequestAttributes;

@Slf4j
@Aspect
@Component
public class RestLoggingAspect {

    @Before("execution(* com.luv2code.health.tracker.rest.controller..*(..)) && args(..)")
    public void logRequest(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("Request - Method: {}, Path: {}", request.getMethod(), request.getRequestURI());

        try {
            String requestBody = getRequestBody(request);
            log.info("Request Body: {}", requestBody);
        } catch (IOException e) {
            log.error("Error reading request body: {}", e.getMessage());
        }
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            return bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    @AfterReturning(pointcut = "execution(* com.luv2code.health.tracker.rest.controller..*(..))", returning = "result")
    public void logResponse(Object result) {
        log.info("Response - Status: {}, Body: {}", SC_OK, result);
    }
}

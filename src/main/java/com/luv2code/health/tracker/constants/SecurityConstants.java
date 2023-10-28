package com.luv2code.health.tracker.constants;

public class SecurityConstants {

    public static final Long ACCESS_TOKEN_EXPIRATION_TIME = 900_000L; // 15 minutes
    public static final Long REFRESH_TOKEN_EXPIRATION_TIME = 777_600_000_0L; // 90 days

    public static final String BEARER_TOKEN_PREFIX = "Bearer ";

    public static final String LOGIN_ENDPOINT = "/api/v1/login";
    public static final String REFRESH_TOKEN_ENDPOINT = "/api/v1/refresh-token";

}

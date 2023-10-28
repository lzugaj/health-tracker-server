package com.luv2code.health.tracker.rest.dto;

import lombok.*;

@Builder
public record AuthDTO (
        String accessToken,
        String refreshToken,
        Long userId,
        String role
) {

}

package com.luv2code.health.tracker.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
public record AuthDTO (
        @NotBlank String accessToken,
        @NotBlank String refreshToken,
        @NotNull Long userId,
        @NotBlank String role
) {

}

package com.luv2code.health.tracker.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RefreshTokenDTO (
        @NotBlank String refreshToken
) {

}

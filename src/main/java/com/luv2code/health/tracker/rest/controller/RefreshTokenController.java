package com.luv2code.health.tracker.rest.controller;

import com.luv2code.health.tracker.rest.dto.AuthDTO;
import com.luv2code.health.tracker.rest.dto.RefreshTokenDTO;
import com.luv2code.health.tracker.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static java.util.UUID.fromString;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Refresh Token", description = "Refresh Token API")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refresh-token")
    @Operation(
            summary = "Refresh token when expired",
            description = "Refresh the token when date expired by sending refresh token in the request body."
    )
    public AuthDTO refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDto) {
        UUID uuid = fromString(refreshTokenDto.refreshToken());
        return refreshTokenService.findByToken(uuid);
    }
}

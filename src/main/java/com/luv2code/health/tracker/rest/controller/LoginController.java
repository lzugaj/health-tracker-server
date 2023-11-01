package com.luv2code.health.tracker.rest.controller;

import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.domain.body_mass_index.BodyMassIndex;
import com.luv2code.health.tracker.exception.HealthTrackerException;
import com.luv2code.health.tracker.rest.dto.AuthDTO;
import com.luv2code.health.tracker.rest.mapper.UserMapper;
import com.luv2code.health.tracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Login", description = "Login API")
public class LoginController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    @Operation(
            summary = "Login into an application",
            description = "Login into an application by sending access token in authorization bearer header."
    )
    public AuthDTO login(@AuthenticationPrincipal Jwt jwt) {
        User user = userMapper.toEntity(jwt);
        return userService.persistUserWithAuthProcessing(user);
    }
}
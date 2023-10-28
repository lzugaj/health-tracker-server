package com.luv2code.health.tracker.rest.mapper;

import com.luv2code.health.tracker.domain.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {

    public User toEntity(Jwt from) {
        Objects.requireNonNull(from, "Given dto is null.");

        return User.builder()
                .firstName(from.getClaim("given_name"))
                .lastName(from.getClaim("family_name"))
                .email(from.getClaim("email"))
                .build();
    }
}

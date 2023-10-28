package com.luv2code.health.tracker.rest.mapper;

import com.luv2code.health.tracker.domain.RefreshToken;
import com.luv2code.health.tracker.domain.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RefreshTokenMapper {

    public RefreshToken toEntity(User from) {
        Objects.requireNonNull(from, "Given entity is null.");

        return RefreshToken.builder()
                .user(from)
                .build();
    }
}

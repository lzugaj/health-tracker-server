package com.luv2code.health.tracker.data;

import com.luv2code.health.tracker.domain.RefreshToken;
import com.luv2code.health.tracker.domain.User;

import java.time.Instant;

import static com.luv2code.health.tracker.util.ClockUtil.useFixedClockAt;
import static java.time.Instant.parse;

public class RefreshTokenData {

    public static RefreshToken createRefreshToken(User user) {
        return RefreshToken.builder()
                .user(user)
                .build();
    }
}

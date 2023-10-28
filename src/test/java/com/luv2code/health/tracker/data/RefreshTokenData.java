package com.luv2code.health.tracker.data;

import com.luv2code.health.tracker.domain.RefreshToken;
import com.luv2code.health.tracker.domain.User;

public class RefreshTokenData {

    public static RefreshToken createRefreshToken(User user) {
        return RefreshToken.builder()
                .user(user)
                .build();
    }
}

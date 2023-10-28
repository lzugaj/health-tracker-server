package com.luv2code.health.tracker.data;

import com.luv2code.health.tracker.rest.dto.RefreshTokenDTO;

public class RefreshTokenDTOData {

    public static RefreshTokenDTO createRefreshTokenDTO(String refreshToken) {
        return RefreshTokenDTO.builder()
                .refreshToken(refreshToken)
                .build();
    }
}

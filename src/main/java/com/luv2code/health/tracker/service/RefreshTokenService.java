package com.luv2code.health.tracker.service;

import com.luv2code.health.tracker.domain.RefreshToken;
import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.exception.RefreshTokenException;
import com.luv2code.health.tracker.properties.TokenProperties;
import com.luv2code.health.tracker.repository.RefreshTokenRepository;
import com.luv2code.health.tracker.rest.dto.AuthDTO;
import com.luv2code.health.tracker.rest.mapper.RefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.luv2code.health.tracker.security.TokenProvider.generateToken;
import static com.luv2code.health.tracker.util.ClockUtil.getClock;
import static java.lang.String.valueOf;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenProperties tokenProperties;
    private final RefreshTokenMapper refreshTokenMapper;

    @Transactional
    public RefreshToken create(User user) {
        RefreshToken refreshToken = refreshTokenMapper.toEntity(user);
        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public AuthDTO findByToken(UUID refreshToken) {
        RefreshToken searchedRefreshToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RefreshTokenException("Refresh token is not founded in database."));

        refreshToken = checkTokenExpirationDate(refreshToken, searchedRefreshToken);
        return buildAuthDTO(searchedRefreshToken.getUser(), refreshToken);
    }

    @Transactional
    public AuthDTO findByUser(User user) {
        RefreshToken searchedRefreshToken = refreshTokenRepository.findByUser(user)
                .orElseThrow(() -> new RefreshTokenException("Refresh token is not founded in database."));

        UUID refreshToken = searchedRefreshToken.getToken();
        refreshToken = checkTokenExpirationDate(refreshToken, searchedRefreshToken);
        return buildAuthDTO(user, refreshToken);
    }

    private UUID checkTokenExpirationDate(UUID refreshToken, RefreshToken searchedRefreshToken) {
        return isExpired(searchedRefreshToken) ?
                deleteAndCreate(searchedRefreshToken) :
                refreshToken;
    }

    private boolean isExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().isBefore(now(getClock()));
    }

    private UUID deleteAndCreate(RefreshToken searchedRefreshToken) {
        refreshTokenRepository.delete(searchedRefreshToken);
        RefreshToken newRefreshToken = create(searchedRefreshToken.getUser());
        return newRefreshToken.getToken();
    }

    private AuthDTO buildAuthDTO(User user, UUID refreshToken) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
        String accessToken = generateToken(userDetails, tokenProperties.getSecret());

        return AuthDTO.builder()
                .accessToken(accessToken)
                .refreshToken(valueOf(refreshToken))
                .userId(user.getId())
                .role(user.getRole().name())
                .build();
    }
}

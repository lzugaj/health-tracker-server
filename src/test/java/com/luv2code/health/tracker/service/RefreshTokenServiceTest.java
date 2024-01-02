package com.luv2code.health.tracker.service;

import com.luv2code.health.tracker.domain.RefreshToken;
import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.exception.RefreshTokenException;
import com.luv2code.health.tracker.properties.TokenProperties;
import com.luv2code.health.tracker.repository.RefreshTokenRepository;
import com.luv2code.health.tracker.rest.dto.AuthDTO;
import com.luv2code.health.tracker.rest.mapper.RefreshTokenMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.luv2code.health.tracker.data.RefreshTokenData.createRefreshToken;
import static com.luv2code.health.tracker.data.UserTestData.createUser;
import static com.luv2code.health.tracker.util.ClockUtil.resetClock;
import static com.luv2code.health.tracker.util.ClockUtil.useFixedClockAt;
import static java.time.Instant.parse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private TokenProperties tokenProperties;

    @Mock
    private RefreshTokenMapper refreshTokenMapper;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    @BeforeEach
    public void setUp() {
        // Set a fixed clock for testing
        Instant fixedInstant = parse("2089-02-12T12:12:44Z");
        useFixedClockAt(fixedInstant);
    }

    @AfterEach
    public void tearDown() {
        // Reset the clock to the system default after the test
        resetClock();
    }

    @Test
    public void user_create_returnNewRefreshToken() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");
        RefreshToken refreshToken = createRefreshToken(user);

        when(refreshTokenMapper.toEntity(user)).thenReturn(refreshToken);
        when(refreshTokenService.create(user)).thenReturn(refreshToken);

        RefreshToken newRefreshToken = refreshTokenService.create(user);

        assertNotNull(newRefreshToken);
        assertEquals(refreshToken, newRefreshToken);
    }

    @Test
    public void refreshTokenUUID_findByToken_throwsRefreshTokenException() {
        UUID randomUUID = UUID.randomUUID();

        when(refreshTokenRepository.findByToken(randomUUID)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                RefreshTokenException.class,
                () -> refreshTokenService.findByToken(randomUUID));

        String expectedMessage = "Refresh token is not found in database.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void refreshTokenUUID_findByToken_returnAuthDTO() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");
        RefreshToken refreshToken = createRefreshToken(user);

        when(refreshTokenRepository.findByToken(refreshToken.getToken())).thenReturn(Optional.of(refreshToken));
        when(customUserDetailsService.loadUserByUsername(user.getEmail())).thenReturn(user);
        when(tokenProperties.getSecret()).thenReturn(SECRET_KEY);

        AuthDTO authDTO = refreshTokenService.findByToken(refreshToken.getToken());

        assertNotNull(authDTO);
        assertEquals(refreshToken.getToken(), UUID.fromString(authDTO.refreshToken()));
        assertEquals("USER", authDTO.role());
    }

    @Test
    public void user_findByUser_throwsRefreshTokenException() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");

        when(refreshTokenRepository.findByUser(user)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                RefreshTokenException.class,
                () -> refreshTokenService.findByUser(user));

        String expectedMessage = "Refresh token is not found in database.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}

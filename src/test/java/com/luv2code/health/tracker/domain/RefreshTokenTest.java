package com.luv2code.health.tracker.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

import static com.luv2code.health.tracker.data.RefreshTokenData.createRefreshToken;
import static com.luv2code.health.tracker.data.UserTestData.createUser;
import static com.luv2code.health.tracker.util.ClockUtil.resetClock;
import static com.luv2code.health.tracker.util.ClockUtil.useFixedClockAt;
import static java.time.Instant.parse;
import static org.junit.jupiter.api.Assertions.*;

public class RefreshTokenTest {

    @BeforeEach
    public void setUp() {
        // Set a fixed clock for testing
        Instant fixedInstant = parse("2023-08-12T14:21:54Z");
        useFixedClockAt(fixedInstant);
    }

    @AfterEach
    public void tearDown() {
        // Reset the clock to the system default after the test
        resetClock();
    }

    @Test
    public void nullUser_whenBuildingEntity_thenThrowNullPointerException() {
        Exception exception = assertThrows(
                NullPointerException.class,
                () -> createRefreshToken(null)
        );

        String expectedMessage = "User must be supplied.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void user_whenBuildingEntity_thenCreateIt() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");
        RefreshToken refreshToken = createRefreshToken(user);

        assertNotNull(refreshToken);
        assertEquals(refreshToken.getUser(), user);
        assertEquals(LocalDateTime.parse("2023-11-10T14:21:54"), refreshToken.getExpiryDate());
    }
}

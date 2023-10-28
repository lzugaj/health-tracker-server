package com.luv2code.health.tracker.domain;

import com.luv2code.health.tracker.domain.body_mass_index.BodyMassIndex;
import com.luv2code.health.tracker.exception.LowerThanOrEqualsZeroException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

import static com.luv2code.health.tracker.data.AdultBodyMassIndexTestData.createAdultBodyMassIndex;
import static com.luv2code.health.tracker.domain.enums.Gender.MALE;
import static com.luv2code.health.tracker.util.ClockUtil.resetClock;
import static com.luv2code.health.tracker.util.ClockUtil.useFixedClockAt;
import static java.time.Instant.parse;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;

public class BodyMassIndexTest {

    @BeforeEach
    public void setUp() {
        // Set a fixed clock for testing
        Instant fixedInstant = parse("2024-02-24T08:23:45Z");
        useFixedClockAt(fixedInstant);
    }

    @AfterEach
    public void tearDown() {
        // Reset the clock to the system default after the test
        resetClock();
    }

    @Test
    public void givenHeightLowerThenZero_whenBuildingEntity_thenThrowNullPointerException() {
        Exception exception = assertThrows(
                LowerThanOrEqualsZeroException.class,
                () -> createAdultBodyMassIndex(-10.0, 100.0, 12, MALE));

        String expectedMessage = "Height cannot be lower then or equals zero.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void givenWeightLowerEqualsZero_whenBuildingEntity_thenThrowException() {
        Exception exception = assertThrows(
                LowerThanOrEqualsZeroException.class,
                () -> createAdultBodyMassIndex(10.0, 0.0, 12, MALE));

        String expectedMessage = "Weight cannot be lower then or equals zero.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void givenBodyMassIndexWithNullValue_whenBuildingEntity_thenThrowException() {
        Exception exception = assertThrows(
                NullPointerException.class,
                () -> createAdultBodyMassIndex(188.00, 82.34,  null, MALE)
        );

        String expectedMessage = "Age must be supplied.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void givenHeightAndWeight_whenBuildingEntity_thenCreateIt() {
        BodyMassIndex newBodyMassIndex = createAdultBodyMassIndex(188.00, 82.34, 35, MALE);

        assertNotNull(newBodyMassIndex);
        assertEquals(188.00, newBodyMassIndex.getHeight());
        assertEquals(82.34, newBodyMassIndex.getWeight());
        assertEquals(23.3, newBodyMassIndex.getValue());
        assertEquals(LocalDateTime.parse("2024-02-24T08:23:45"), newBodyMassIndex.getCreatedAt());
        assertEquals(LocalDateTime.parse("2024-02-24T08:23:45"), newBodyMassIndex.getModifiedAt());
    }

    @Test
    public void givenBodyMassIndex_whenUpdate_thenUpdateAndValidate() {
        BodyMassIndex bodyMassIndex = createAdultBodyMassIndex(188.00, 82.34, 35, MALE);
        BodyMassIndex updatedBodyMassIndex = createAdultBodyMassIndex(188.0, 90.2, 40, MALE);

        bodyMassIndex.update(updatedBodyMassIndex);

        assertEquals(188.0, bodyMassIndex.getHeight());
        assertEquals(90.2, bodyMassIndex.getWeight());
        assertEquals(25.52, bodyMassIndex.getValue());
        assertEquals(LocalDateTime.parse("2024-02-24T08:23:45"), updatedBodyMassIndex.getModifiedAt());
    }
}

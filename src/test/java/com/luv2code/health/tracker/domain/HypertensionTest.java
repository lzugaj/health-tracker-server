package com.luv2code.health.tracker.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

import static com.luv2code.health.tracker.data.HypertensionData.createHypertension;
import static com.luv2code.health.tracker.util.ClockUtil.resetClock;
import static com.luv2code.health.tracker.util.ClockUtil.useFixedClockAt;
import static java.time.Instant.parse;
import static org.junit.jupiter.api.Assertions.*;

public class HypertensionTest {

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
    public void givenNullValueForDiastolicValue_whenBuildingEntity_thenThrowNullPointerException() {
        Exception exception = assertThrows(
                NullPointerException.class,
                () -> createHypertension(123, null)
        );

        String expectedMessage = "Diastolic pressure must be supplied.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void givenValidHypertension_whenBuildingEntity_thenCreateIt() {
        Hypertension hypertension = createHypertension(129, 72);

        assertNotNull(hypertension);
        assertEquals(129, hypertension.getSystolic());
        assertEquals(72, hypertension.getDiastolic());
        assertEquals(LocalDateTime.parse("2024-02-24T08:23:45"), hypertension.getCreatedAt());
        assertEquals(LocalDateTime.parse("2024-02-24T08:23:45"), hypertension.getModifiedAt());
    }

    @Test
    public void givenValidHypertension_whenUpdate_thenUpdateAndValidate() {
        Hypertension hypertension = createHypertension(129, 72);
        Hypertension updatedHypertension = createHypertension(150, 89);

        hypertension.update(updatedHypertension);

        assertNotNull(hypertension);
        assertEquals(150, hypertension.getSystolic());
        assertEquals(89, hypertension.getDiastolic());
        assertEquals(LocalDateTime.parse("2024-02-24T08:23:45"), hypertension.getModifiedAt());
    }
}

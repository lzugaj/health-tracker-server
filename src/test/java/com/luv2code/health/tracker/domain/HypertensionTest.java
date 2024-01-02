package com.luv2code.health.tracker.domain;

import org.junit.jupiter.api.Test;

import static com.luv2code.health.tracker.data.HypertensionData.createHypertension;
import static org.junit.jupiter.api.Assertions.*;

public class HypertensionTest {

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
    }

    @Test
    public void givenValidHypertension_whenUpdate_thenUpdateAndValidate() {
        Hypertension hypertension = createHypertension(129, 72);
        Hypertension updatedHypertension = createHypertension(150, 89);

        hypertension.update(updatedHypertension);

        assertNotNull(hypertension);
        assertEquals(150, hypertension.getSystolic());
        assertEquals(89, hypertension.getDiastolic());
    }
}

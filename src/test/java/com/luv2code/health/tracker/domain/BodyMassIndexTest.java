package com.luv2code.health.tracker.domain;

import com.luv2code.health.tracker.domain.body_mass_index.BodyMassIndex;
import com.luv2code.health.tracker.exception.LowerThanOrEqualsZeroException;
import org.junit.jupiter.api.Test;

import static com.luv2code.health.tracker.data.AdultBodyMassIndexTestData.createAdultBodyMassIndex;
import static com.luv2code.health.tracker.domain.enums.Gender.MALE;
import static org.junit.jupiter.api.Assertions.*;

public class BodyMassIndexTest {

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
    }

    @Test
    public void givenBodyMassIndex_whenUpdate_thenUpdateAndValidate() {
        BodyMassIndex bodyMassIndex = createAdultBodyMassIndex(188.00, 82.34, 35, MALE);
        BodyMassIndex updatedBodyMassIndex = createAdultBodyMassIndex(188.0, 90.2, 40, MALE);

        bodyMassIndex.update(updatedBodyMassIndex);

        assertEquals(188.0, bodyMassIndex.getHeight());
        assertEquals(90.2, bodyMassIndex.getWeight());
        assertEquals(25.52, bodyMassIndex.getValue());
    }
}

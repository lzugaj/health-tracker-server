package com.luv2code.health.tracker.domain;

import com.luv2code.health.tracker.data.BodyMassIndexTestData;
import com.luv2code.health.tracker.exception.LowerThanOrEqualsZeroException;
import com.luv2code.health.tracker.rest.dto.BodyMassIndexDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.luv2code.health.tracker.data.BodyMassIndexTestData.*;
import static org.junit.jupiter.api.Assertions.*;

public class BodyMassIndexTest {

    @Test
    public void givenHeightLowerThenOrEqualsZeroAndWeight_whenBuildingEntity_thenThrowsException() {
        Exception exception = assertThrows(
                LowerThanOrEqualsZeroException.class,
                () -> createBodyMassIndex(-10.0, 100.0));

        String expectedMessage = "Height cannot be lower then or equals zero.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void givenHeightAndWeightLowerThenOrEqualsZero_whenBuildingEntity_thenThrowsException() {
        Exception exception = assertThrows(
                LowerThanOrEqualsZeroException.class,
                () -> createBodyMassIndex(10.0, 0.0));

        String expectedMessage = "Weight cannot be lower then or equals zero.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void givenHeightAndWeight_whenBuildingEntity_thenCreateIt() {
        BodyMassIndex newBodyMassIndex = createBodyMassIndex(188.00, 82.34);

        assertNotNull(newBodyMassIndex);
        assertEquals(188.00, newBodyMassIndex.getHeight());
        assertEquals(82.34, newBodyMassIndex.getWeight());
        assertEquals(23.3, newBodyMassIndex.getValue());
    }

    @Test
    public void givenBMI_whenUpdate_thenModifyIt() {
        BodyMassIndex bodyMassIndex = createBodyMassIndex(1L, 193.50, 102.00, 23.44, LocalDateTime.now(), LocalDateTime.now());

        BodyMassIndexDTO dto = BodyMassIndexDTO.builder()
                .height(193.50)
                .weight(90.00)
                .build();

        bodyMassIndex.update(dto);

        assertNotNull(bodyMassIndex);
        assertEquals(193.50, bodyMassIndex.getHeight());
        assertEquals(90.00, bodyMassIndex.getWeight());
        assertEquals(24.04, bodyMassIndex.getValue());
    }
}

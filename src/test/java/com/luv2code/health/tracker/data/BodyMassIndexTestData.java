package com.luv2code.health.tracker.data;

import com.luv2code.health.tracker.domain.BodyMassIndex;

import java.time.LocalDateTime;

public class BodyMassIndexTestData {

    public static BodyMassIndex createBodyMassIndex(Double height, Double weight) {
        return new BodyMassIndex(height, weight);
    }

    public static BodyMassIndex createBodyMassIndex(Long id, Double height, Double weight, Double value,
                                          LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new BodyMassIndex(id, height, weight, value, createdAt, modifiedAt);
    }
}

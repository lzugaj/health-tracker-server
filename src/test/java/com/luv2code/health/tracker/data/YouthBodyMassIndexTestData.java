package com.luv2code.health.tracker.data;

import com.luv2code.health.tracker.domain.body_mass_index.AdultBodyMassIndex;
import com.luv2code.health.tracker.domain.body_mass_index.BodyMassIndex;
import com.luv2code.health.tracker.domain.body_mass_index.YouthBodyMassIndex;
import com.luv2code.health.tracker.domain.enums.Gender;

import java.time.LocalDateTime;

public class YouthBodyMassIndexTestData {

    public static YouthBodyMassIndex createYouthBodyMassIndex(Double height, Double weight, Integer age, Gender gender) {
        return new YouthBodyMassIndex(height, weight, age, gender);
    }
}

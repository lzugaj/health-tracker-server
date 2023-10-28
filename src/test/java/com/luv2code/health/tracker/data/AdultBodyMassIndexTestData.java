package com.luv2code.health.tracker.data;

import com.luv2code.health.tracker.domain.body_mass_index.AdultBodyMassIndex;
import com.luv2code.health.tracker.domain.enums.Gender;

import java.time.LocalDateTime;

public class AdultBodyMassIndexTestData {

    public static AdultBodyMassIndex createAdultBodyMassIndex(Double height, Double weight, Integer age, Gender gender) {
        return new AdultBodyMassIndex(height, weight, age, gender);
    }
}

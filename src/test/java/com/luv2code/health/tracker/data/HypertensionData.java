package com.luv2code.health.tracker.data;

import com.luv2code.health.tracker.domain.Hypertension;

public class HypertensionData {

    public static Hypertension createHypertension(Integer systolic, Integer diastolic) {
        return Hypertension.builder()
                .systolic(systolic)
                .diastolic(diastolic)
                .build();
    }
}

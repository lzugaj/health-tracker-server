package com.luv2code.health.tracker.domain.body_mass_index;

import com.luv2code.health.tracker.domain.enums.Gender;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@DiscriminatorValue("ADULT")
public class AdultBodyMassIndex extends BodyMassIndex {

    @Builder
    public AdultBodyMassIndex(
            Double height,
            Double weight,
            Integer age,
            Gender gender
    ) {
        super(height, weight, age, gender);
    }
}

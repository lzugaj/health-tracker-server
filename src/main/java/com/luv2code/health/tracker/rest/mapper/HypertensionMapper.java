package com.luv2code.health.tracker.rest.mapper;

import com.luv2code.health.tracker.domain.Hypertension;
import com.luv2code.health.tracker.domain.body_mass_index.AdultBodyMassIndex;
import com.luv2code.health.tracker.domain.body_mass_index.BodyMassIndex;
import com.luv2code.health.tracker.domain.body_mass_index.YouthBodyMassIndex;
import com.luv2code.health.tracker.domain.enums.Gender;
import com.luv2code.health.tracker.rest.dto.BodyMassIndexDTO;
import com.luv2code.health.tracker.rest.dto.HypertensionDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.luv2code.health.tracker.domain.enums.Gender.FEMALE;
import static com.luv2code.health.tracker.domain.enums.Gender.MALE;

@Component
public class HypertensionMapper {

    public Hypertension toEntity(HypertensionDTO dto) {
        Objects.requireNonNull(dto, "Given dto is null.");

        return Hypertension.builder()
                .systolic(dto.systolic())
                .diastolic(dto.diastolic())
                .build();
    }

    public HypertensionDTO toDto(Hypertension entity) {
        Objects.requireNonNull(entity, "Given entity is null.");
        
        return HypertensionDTO.builder()
                .id(entity.getId())
                .systolic(entity.getSystolic())
                .diastolic(entity.getDiastolic())
                .build();
    }
}

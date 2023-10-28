package com.luv2code.health.tracker.rest.mapper;

import com.luv2code.health.tracker.domain.body_mass_index.AdultBodyMassIndex;
import com.luv2code.health.tracker.domain.body_mass_index.BodyMassIndex;
import com.luv2code.health.tracker.domain.body_mass_index.YouthBodyMassIndex;
import com.luv2code.health.tracker.domain.enums.Gender;
import com.luv2code.health.tracker.rest.dto.BodyMassIndexDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.luv2code.health.tracker.domain.enums.Gender.FEMALE;
import static com.luv2code.health.tracker.domain.enums.Gender.MALE;

@Component
public class BodyMassIndexMapper implements HealthTrackerMapper<BodyMassIndex, BodyMassIndexDTO> {

    @Override
    public BodyMassIndex toEntity(BodyMassIndexDTO dto) {
        Objects.requireNonNull(dto, "Given dto is null.");

        Gender gender = "FEMALE".equals(dto.gender()) ? FEMALE : MALE;
        if (dto.age() < 20) {
            return YouthBodyMassIndex.builder()
                    .height(dto.height())
                    .weight(dto.weight())
                    .age(dto.age())
                    .gender(gender)
                    .build();
        } else {
            return AdultBodyMassIndex.builder()
                    .height(dto.height())
                    .weight(dto.weight())
                    .age(dto.age())
                    .gender(gender)
                    .build();
        }
    }

    @Override
    public BodyMassIndexDTO toDto(BodyMassIndex entity) {
        Objects.requireNonNull(entity, "Given entity is null.");
        
        return BodyMassIndexDTO.builder()
                .id(entity.getId())
                .height(entity.getHeight())
                .weight(entity.getWeight())
                .age(entity.getAge())
                .gender(entity.getGender().name())
                .value(entity.getValue())
                .build();
    }
}

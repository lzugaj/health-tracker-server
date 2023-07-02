package com.luv2code.health.tracker.rest.mapper;

import com.luv2code.health.tracker.domain.BodyMassIndex;
import com.luv2code.health.tracker.rest.dto.BodyMassIndexDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BodyMassIndexMapper implements HealthTrackerMapper<BodyMassIndexDTO, BodyMassIndex> {

    @Override
    public BodyMassIndex toEntity(BodyMassIndexDTO dto) {
        Objects.requireNonNull(dto, "Given dto is null.");
        return BodyMassIndex.builder()
                .height(dto.getHeight())
                .weight(dto.getWeight())
                .build();
    }

    @Override
    public BodyMassIndexDTO toDto(BodyMassIndex entity) {
        Objects.requireNonNull(entity, "Given entity is null.");
        return BodyMassIndexDTO.builder()
                .id(entity.getId())
                .height(entity.getHeight())
                .weight(entity.getWeight())
                .value(entity.getValue())
                .build();
    }
}

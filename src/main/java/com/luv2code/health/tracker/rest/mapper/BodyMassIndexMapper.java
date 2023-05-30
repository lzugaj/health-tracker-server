package com.luv2code.health.tracker.rest.mapper;

import com.luv2code.health.tracker.domain.BodyMassIndex;
import com.luv2code.health.tracker.rest.dto.BodyMassIndexDTO;
import org.springframework.stereotype.Component;

@Component
public class BodyMassIndexMapper implements HealthTrackerMapper<BodyMassIndexDTO, BodyMassIndex> {

    @Override
    public BodyMassIndex toEntity(BodyMassIndexDTO dto) {
        if (dto == null) {
            throw new NullPointerException("Given dto is null.");
        }

        return BodyMassIndex.builder()
                .height(dto.getHeight())
                .weight(dto.getWeight())
                .build();
    }

    @Override
    public BodyMassIndexDTO toDto(BodyMassIndex entity) {
        if (entity == null) {
            throw new NullPointerException("Given entity is null.");
        }

        return BodyMassIndexDTO.builder()
                .id(entity.getId())
                .height(entity.getHeight())
                .weight(entity.getWeight())
                .value(entity.getValue())
                .build();
    }
}

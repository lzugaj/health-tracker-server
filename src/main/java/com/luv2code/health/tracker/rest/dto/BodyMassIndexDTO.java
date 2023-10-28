package com.luv2code.health.tracker.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
public record BodyMassIndexDTO (
        Long id,
        @NotNull Double height,
        @NotNull Double weight,
        @NotNull Integer age,
        @NotNull String gender,
        Double value
) {

}

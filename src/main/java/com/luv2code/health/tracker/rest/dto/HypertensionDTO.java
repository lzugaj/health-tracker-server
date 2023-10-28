package com.luv2code.health.tracker.rest.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
public record HypertensionDTO (
        Long id,
        @NotNull Integer systolic,
        @NotNull Integer diastolic
) {

}

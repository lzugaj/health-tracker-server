package com.luv2code.health.tracker.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BodyMassIndexDTO {

    private Long id;

    @NotNull
    private Double height;

    @NotNull
    private Double weight;

    private Double value;

}

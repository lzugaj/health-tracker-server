package com.luv2code.health.tracker.domain;

import com.luv2code.health.tracker.exception.LowerThanOrEqualsZeroException;
import com.luv2code.health.tracker.rest.dto.BodyMassIndexDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "body_mass_index")
public class BodyMassIndex {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_body_mass_index"
    )
    @SequenceGenerator(
            name = "seq_body_mass_index",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "height", nullable = false)
    private Double height;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Double weight;

    @NotNull
    @Column(name = "value", nullable = false)
    private Double value;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @Builder
    public BodyMassIndex(Double height, Double weight) {
        checkDomain(height, weight);

        this.height = height;
        this.weight = weight;
        this.value = calculateBMIValue(height, weight);
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void update(BodyMassIndexDTO dto) {
        checkDomain(dto.getHeight(), dto.getWeight());

        this.height = dto.getHeight();
        this.weight = dto.getWeight();
        this.value = calculateBMIValue(dto.getHeight(), dto.getWeight());
        this.modifiedAt = LocalDateTime.now();
    }

    private void checkDomain(Double height, Double weight) {
        if (height <= 0) {
            throw new LowerThanOrEqualsZeroException("Height cannot be lower then or equals zero.");
        }

        if (weight <= 0) {
            throw new LowerThanOrEqualsZeroException("Weight cannot be lower then or equals zero.");
        }
    }

    private Double calculateBMIValue(Double height, Double weight) {
        double heightInMeters = height / 100;
        return Math.round(weight / (heightInMeters * heightInMeters) * 100.0) / 100.0;
    }
}
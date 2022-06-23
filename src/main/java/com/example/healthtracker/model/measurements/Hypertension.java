package com.example.healthtracker.model.measurements;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "hypertension")
@PrimaryKeyJoinColumn(name = "condition_measurement_id")
public class Hypertension extends ConditionMeasurement {

    @Column(name = "systolic", nullable = false)
    private Double systolic;

    @Column(name = "diastolic", nullable = false)
    private Double diastolic;

}

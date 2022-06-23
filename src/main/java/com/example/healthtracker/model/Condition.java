package com.example.healthtracker.model;

import com.example.healthtracker.model.base.BaseEntity;
import com.example.healthtracker.model.measurements.ConditionMeasurement;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(
        name = "condition",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "name" })
        }
)
public class Condition extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @OneToMany(
            mappedBy = "condition",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserCondition> userConditions;

    @OneToMany(
            mappedBy = "condition",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ConditionMeasurement> conditionMeasurements;

    public void addUserCondition(final UserCondition userCondition) {
        if (userConditions == null) {
            userConditions = new ArrayList<>();
        }

        userConditions.add(userCondition);
    }

    public void removeUserCondition(final UserCondition userCondition) {
        userConditions.remove(userCondition);
    }

    public void addConditionMeasurement(final ConditionMeasurement conditionMeasurement) {
        if (conditionMeasurements == null) {
            conditionMeasurements = new ArrayList<>();
        }

        conditionMeasurements.add(conditionMeasurement);
    }

    public void removeConditionMeasurement(final ConditionMeasurement conditionMeasurement) {
        conditionMeasurements.remove(conditionMeasurement);
    }
}

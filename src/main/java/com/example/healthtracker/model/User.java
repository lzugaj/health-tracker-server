package com.example.healthtracker.model;

import com.example.healthtracker.model.base.BaseEntity;
import com.example.healthtracker.model.bmi.BodyMassIndex;
import com.example.healthtracker.model.enums.GenderType;
import com.example.healthtracker.model.measurements.ConditionMeasurement;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "email" })
        }
)
public class User extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    private String confirmationPassword;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserCondition> userConditions;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BodyMassIndex> bodyMassIndices;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ConditionMeasurement> conditionMeasurements;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public void addUserCondition(final UserCondition userCondition) {
        if (userConditions == null) {
            userConditions = new ArrayList<>();
        }

        userConditions.add(userCondition);
    }

    public void removeUserCondition(final UserCondition userCondition) {
        userConditions.remove(userCondition);
    }

    public void addBodyMassIndex(final BodyMassIndex bodyMassIndex) {
        if (bodyMassIndices == null) {
            bodyMassIndices = new ArrayList<>();
        }

        bodyMassIndices.add(bodyMassIndex);
    }

    public void removeBodyMassIndex(final BodyMassIndex bodyMassIndex) {
        bodyMassIndices.remove(bodyMassIndex);
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

    public void addRole(final Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }

        roles.add(role);
    }

    public void removeRole(final Role role) {
        roles.remove(role);
    }
}

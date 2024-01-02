package com.luv2code.health.tracker.domain.body_mass_index;

import com.luv2code.health.tracker.domain.enums.Gender;
import com.luv2code.health.tracker.exception.LowerThanOrEqualsZeroException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.DiscriminatorType.STRING;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static jakarta.persistence.InheritanceType.SINGLE_TABLE;
import static java.lang.Math.round;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(
        name = "body_mass_index_type",
        discriminatorType = STRING
)
@Table(name = "body_mass_index")
@EntityListeners(AuditingEntityListener.class)
public abstract class BodyMassIndex implements Comparable<BodyMassIndex> {

    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
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
    @Column(name = "age", nullable = false)
    private Integer age; // TODO: Remove to user, create date of birth field

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender; // TODO: Remove to user

    @NotNull
    @Column(name = "value", nullable = false)
    private Double value;

    @NotNull
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "last_modified_at", insertable = false)
    private LocalDateTime lastModifiedAt;

    @NotNull
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by", insertable = false)
    private Long lastModifiedBy;

    public BodyMassIndex(Double height, Double weight, Integer age, Gender gender) {
        checkDomain(height, weight);

        this.height = requireNonNull(height, "Weight must be supplied.");
        this.weight = requireNonNull(weight, "Height must be supplied.");
        this.age = requireNonNull(age, "Age must be supplied.");
        this.gender = requireNonNull(gender, "Gender must be supplied.");
        this.value = calculateBMIValue(height, weight);
    }

    public void update(BodyMassIndex bodyMassIndex) {
        checkDomain(bodyMassIndex.getHeight(), bodyMassIndex.getWeight());

        this.height = requireNonNull(bodyMassIndex.getHeight(), "Weight must be supplied.");
        this.weight = requireNonNull(bodyMassIndex.getWeight(), "Height must be supplied.");
        this.value = calculateBMIValue(bodyMassIndex.getHeight(), bodyMassIndex.getWeight());
    }

    private static void checkDomain(Double height, Double weight) {
        if (height <= 0) {
            throw new LowerThanOrEqualsZeroException("Height cannot be lower then or equals zero.");
        }

        if (weight <= 0) {
            throw new LowerThanOrEqualsZeroException("Weight cannot be lower then or equals zero.");
        }
    }

    private static Double calculateBMIValue(Double height, Double weight) {
        double heightInMeters = height / 100;
        return round(weight / (heightInMeters * heightInMeters) * 100.0) / 100.0;
    }

    @Override
    public int compareTo(BodyMassIndex other) {
        if (this.getCreatedAt().isBefore(other.getCreatedAt())) return 0;
        if (this.getCreatedAt().isAfter(other.getCreatedAt())) return 1;
        if (this.getCreatedAt().isEqual(other.getCreatedAt())) return 0; // TODO: Maybe compare by value then?
        return -1;
    }
}
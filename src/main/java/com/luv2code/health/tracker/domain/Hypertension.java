package com.luv2code.health.tracker.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.luv2code.health.tracker.util.ClockUtil.getClock;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "hypertension")
public class Hypertension {

    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "seq_hypertension"
    )
    @SequenceGenerator(
            name = "seq_hypertension",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "systolic", nullable = false)
    private Integer systolic;

    @Column(name = "diastolic", nullable = false)
    private Integer diastolic;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @Builder(toBuilder = true)
    public Hypertension(
            Integer systolic,
            Integer diastolic
    ) {
        this.systolic = requireNonNull(systolic, "Systolic pressure must be supplied.");
        this.diastolic = requireNonNull(diastolic, "Diastolic pressure must be supplied.");
        this.createdAt = now(getClock());
        this.modifiedAt = now(getClock());
    }

    public void update(Hypertension hypertension) {
        this.systolic = requireNonNull(hypertension.getSystolic(), "Systolic pressure must be supplied.");
        this.diastolic = requireNonNull(hypertension.getDiastolic(), "Diastolic pressure must be supplied.");
        this.modifiedAt = now(getClock());
    }
}

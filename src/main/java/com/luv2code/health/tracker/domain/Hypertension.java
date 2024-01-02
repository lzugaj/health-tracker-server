package com.luv2code.health.tracker.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "hypertension")
@EntityListeners(AuditingEntityListener.class)
public class Hypertension implements Comparable<Hypertension> {

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

    @Builder(toBuilder = true)
    public Hypertension(
            Integer systolic,
            Integer diastolic
    ) {
        this.systolic = requireNonNull(systolic, "Systolic pressure must be supplied.");
        this.diastolic = requireNonNull(diastolic, "Diastolic pressure must be supplied.");
    }

    public void update(Hypertension hypertension) {
        this.systolic = requireNonNull(hypertension.getSystolic(), "Systolic pressure must be supplied.");
        this.diastolic = requireNonNull(hypertension.getDiastolic(), "Diastolic pressure must be supplied.");
    }

    @Override
    public int compareTo(Hypertension other) {
        if (this.getCreatedAt().isBefore(other.getCreatedAt())) return 0;
        if (this.getCreatedAt().isAfter(other.getCreatedAt())) return 1;
        if (this.getCreatedAt().isEqual(other.getCreatedAt())) return 0; // TODO: Maybe compare by value then?
        return -1;
    }
}

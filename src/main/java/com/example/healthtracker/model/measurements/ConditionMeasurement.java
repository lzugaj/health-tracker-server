package com.example.healthtracker.model.measurements;

import com.example.healthtracker.model.Condition;
import com.example.healthtracker.model.User;
import com.example.healthtracker.model.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "condition_measurement")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ConditionMeasurement extends BaseEntity {

    @Column(name = "measured_at", nullable = false)
    private LocalDateTime measuredAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_id", nullable = false)
    private Condition condition;

}

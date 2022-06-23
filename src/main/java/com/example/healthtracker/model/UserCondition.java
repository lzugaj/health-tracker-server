package com.example.healthtracker.model;

import com.example.healthtracker.model.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_condition")
public class UserCondition extends BaseEntity {

    @Column(name = "is_selected", nullable = false)
    private Boolean isSelected;

    @Column(name = "initially_activated_at", nullable = false)
    private LocalDateTime initiallyActivatedAt;

    @Column(name = "lastly_activated_at")
    private LocalDateTime lastlyActivatedAt;

    @Column(name = "lastly_deactivated_at")
    private LocalDateTime lastlyDeactivatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_id", nullable = false)
    private Condition condition;

}

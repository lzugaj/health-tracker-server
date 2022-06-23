package com.example.healthtracker.model.bmi;

import com.example.healthtracker.model.User;
import com.example.healthtracker.model.base.BaseEntity;
import com.example.healthtracker.model.enums.GenderType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "body_mass_index")
public class BodyMassIndex extends BaseEntity {

    @Column(name = "height", nullable = false)
    private Double height;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "bmi_value", nullable = false)
    private Double bmiValue;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "body_mass_index_range_info_id")
    private BodyMassIndexRangeInfo bodyMassIndexRangeInfo;

}

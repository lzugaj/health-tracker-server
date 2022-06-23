package com.example.healthtracker.model.bmi;

import com.example.healthtracker.model.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "body_mass_index_range_info")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "body_mass_index_range")
public abstract class BodyMassIndexRangeInfo extends BaseEntity {

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "range_info", nullable = false)
    private String rangeInfo;

    @Column(name = "color_hash", nullable = false)
    private String colorHash;

    @OneToMany(
            mappedBy = "bodyMassIndexRangeInfo",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<BodyMassIndex> bodyMassIndices;

    public void addBodyMassIndex(final BodyMassIndex bodyMassIndex) {
        if (bodyMassIndices == null) {
            bodyMassIndices = new ArrayList<>();
        }

        bodyMassIndices.add(bodyMassIndex);
    }

    public void removeBodyMassIndex(final BodyMassIndex bodyMassIndex) {
        bodyMassIndices.remove(bodyMassIndex);
    }
}

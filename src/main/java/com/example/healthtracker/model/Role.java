package com.example.healthtracker.model;

import com.example.healthtracker.model.base.BaseEntity;
import com.example.healthtracker.model.enums.RoleType;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(
        name = "role",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "name" })
        }
)
public class Role extends BaseEntity {

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}

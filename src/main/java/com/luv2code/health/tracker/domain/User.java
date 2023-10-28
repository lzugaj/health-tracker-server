package com.luv2code.health.tracker.domain;

import com.luv2code.health.tracker.domain.body_mass_index.BodyMassIndex;
import com.luv2code.health.tracker.domain.enums.Role;
import com.luv2code.health.tracker.util.ClockUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

import static com.luv2code.health.tracker.domain.enums.Role.USER;
import static com.luv2code.health.tracker.util.ClockUtil.getClock;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static java.time.LocalDateTime.now;
import static java.util.Collections.singleton;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "seq_user_index"
    )
    @SequenceGenerator(
            name = "seq_user_index",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Enumerated(STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @OneToMany(
            cascade = ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "user_id", nullable = false)
    private Set<BodyMassIndex> bodyMassIndices;

    @OneToMany(
            cascade = ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "user_id", nullable = false)
    private Set<Hypertension> hypertensions;

    @Builder(toBuilder = true)
    public User(String firstName, String lastName, String email) {
        this.firstName = requireNonNull(firstName, "First name must be supplied.");
        this.lastName = requireNonNull(lastName, "Last name must be supplied.");
        this.email = requireNonNull(email, "Email must be supplied.");
        this.role = USER;
        this.createdAt = now(getClock());
        this.modifiedAt = now(getClock());
        this.bodyMassIndices = new HashSet<>();
        this.hypertensions = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return singleton(
                new SimpleGrantedAuthority(
                        String.format("ROLE_%s", this.role.name())
                )
        );
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void update(User user) {
        this.firstName = requireNonNull(user.getFirstName(), "First name must be supplied.");
        this.lastName = requireNonNull(user.getLastName(), "Last name must be supplied.");
        this.email = requireNonNull(user.getEmail(), "Email must be supplied.");
        this.modifiedAt = now(getClock());
    }

    public void addBodyMassIndex(BodyMassIndex bodyMassIndex) {
        this.bodyMassIndices.add(requireNonNull(bodyMassIndex, "Body mass index must be supplied."));
    }

    public void removeBodyMassIndex(BodyMassIndex bodyMassIndex) {
        this.bodyMassIndices.remove(requireNonNull(bodyMassIndex, "Body mass index must be supplied."));
    }

    public void addHypertension(Hypertension hypertension) {
        this.hypertensions.add(requireNonNull(hypertension, "Hypertension must be supplied."));
    }

    public void removeHypertension(Hypertension hypertension) {
        this.hypertensions.remove(requireNonNull(hypertension, "Hypertension must be supplied."));
    }
}

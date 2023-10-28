package com.luv2code.health.tracker.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.luv2code.health.tracker.constants.SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME;
import static com.luv2code.health.tracker.util.ClockUtil.getClock;
import static java.time.Instant.now;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneOffset.UTC;
import static java.util.Objects.requireNonNull;
import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_refresh_token"
    )
    @SequenceGenerator(
            name = "seq_refresh_token",
            allocationSize = 1
    )
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "token", nullable = false, unique = true)
    private UUID token;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Builder(toBuilder = true)
    public RefreshToken(User user) {
        this.user = requireNonNull(user, "User must be supplied.");
        this.token = randomUUID();
        this.expiryDate = ofInstant(now(getClock()).plusMillis(REFRESH_TOKEN_EXPIRATION_TIME), UTC);
    }
}

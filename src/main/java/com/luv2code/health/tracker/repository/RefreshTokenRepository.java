package com.luv2code.health.tracker.repository;

import com.luv2code.health.tracker.domain.RefreshToken;
import com.luv2code.health.tracker.domain.User;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends BaseJpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(UUID token);

    Optional<RefreshToken> findByUser(User user);

}

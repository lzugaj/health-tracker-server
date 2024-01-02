package com.luv2code.health.tracker.repository;

import com.luv2code.health.tracker.domain.User;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseJpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}

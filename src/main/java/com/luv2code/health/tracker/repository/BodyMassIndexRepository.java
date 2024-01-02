package com.luv2code.health.tracker.repository;

import com.luv2code.health.tracker.domain.body_mass_index.BodyMassIndex;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyMassIndexRepository extends BaseJpaRepository<BodyMassIndex, Long> {

}

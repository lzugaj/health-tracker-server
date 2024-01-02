package com.luv2code.health.tracker.repository;

import com.luv2code.health.tracker.domain.Hypertension;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HypertensionRepository extends BaseJpaRepository<Hypertension, Long> {

}

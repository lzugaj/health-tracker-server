package com.luv2code.health.tracker.repository;

import com.luv2code.health.tracker.domain.Hypertension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HypertensionRepository extends JpaRepository<Hypertension, Long> {

}

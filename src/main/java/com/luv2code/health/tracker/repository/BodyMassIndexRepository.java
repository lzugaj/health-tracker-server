package com.luv2code.health.tracker.repository;

import com.luv2code.health.tracker.domain.BodyMassIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyMassIndexRepository extends JpaRepository<BodyMassIndex, Long> {

}

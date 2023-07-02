package com.luv2code.health.tracker.service;

import com.luv2code.health.tracker.domain.BodyMassIndex;
import com.luv2code.health.tracker.repository.BodyMassIndexRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BodyMassIndexService {

    private final BodyMassIndexRepository bodyMassIndexRepository;

    @Transactional
    public void save(BodyMassIndex bodyMassIndex) {
        bodyMassIndexRepository.save(bodyMassIndex);
    }

    public BodyMassIndex findById(Long id) {
        return bodyMassIndexRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Cannot find searched Body Mass Index. [id=%d]", id)));
    }

    @Transactional(readOnly = true)
    public List<BodyMassIndex> findAll() {
        return bodyMassIndexRepository.findAll().stream()
                .sorted(Comparator.comparing(BodyMassIndex::getCreatedAt))
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long id, BodyMassIndex bodyMassIndex) {
        bodyMassIndexRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Cannot find searched Body Mass Index. [id=%d]", id)))
                .update(bodyMassIndex);
    }

    @Transactional
    public void delete(Long id) {
        bodyMassIndexRepository.deleteById(id);
    }
}

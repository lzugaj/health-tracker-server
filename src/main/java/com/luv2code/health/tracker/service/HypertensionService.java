package com.luv2code.health.tracker.service;

import com.luv2code.health.tracker.domain.Hypertension;
import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.repository.HypertensionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toCollection;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HypertensionService {

    private final HypertensionRepository hypertensionRepository;
    private final UserService userService;

    @Transactional
    public void save(Hypertension hypertension) {
        User currentLoggedInUser = userService.findCurrentLoggedInUser();
        currentLoggedInUser.addHypertension(hypertension);
    }

    public Hypertension findById(Long id) {
        return hypertensionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Cannot found searched hypertension by id. [id=%d]", id)
                ));
    }

    public Set<Hypertension> findAllForCurrentUser() {
        return userService.findCurrentLoggedInUser()
                .getHypertensions()
                .stream()
                .sorted(comparing(Hypertension::getCreatedAt))
                .collect(toCollection(LinkedHashSet::new));
    }

    @Transactional
    public void update(Long id, Hypertension hypertension) {
        findById(id).update(hypertension);
    }

    @Transactional
    public void delete(Long id) {
        Hypertension searchedHypertension = findById(id);
        User currentLoggedInUser = userService.findCurrentLoggedInUser();
        currentLoggedInUser.removeHypertension(searchedHypertension);
    }
}

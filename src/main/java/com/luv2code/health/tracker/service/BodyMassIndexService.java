package com.luv2code.health.tracker.service;

import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.domain.body_mass_index.BodyMassIndex;
import com.luv2code.health.tracker.repository.BodyMassIndexRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toCollection;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BodyMassIndexService {

    private final BodyMassIndexRepository bodyMassIndexRepository;
    private final UserService userService;

    @Transactional
    public void save(BodyMassIndex bodyMassIndex) {
        User currentLoggedInUser = userService.findCurrentLoggedInUser();
        currentLoggedInUser.addBodyMassIndex(bodyMassIndex);
    }

    public BodyMassIndex findById(Long id) {
        return bodyMassIndexRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Cannot find searched Body Mass Index by given id. [id=%d]", id)));
    }

    public Set<BodyMassIndex> findAllForCurrentUser() {
        return userService.findCurrentLoggedInUser()
                .getBodyMassIndices()
                .stream()
                .sorted(comparing(BodyMassIndex::getCreatedAt))
                .collect(toCollection(LinkedHashSet::new));
    }

    @Transactional
    public void update(Long id, BodyMassIndex bodyMassIndex) {
        findById(id).update(bodyMassIndex);
    }

    @Transactional
    public void delete(Long id) {
        BodyMassIndex searchedBodyMassIndex = findById(id);
        User currentLoggedInUser = userService.findCurrentLoggedInUser();
        currentLoggedInUser.removeBodyMassIndex(searchedBodyMassIndex);
    }
}

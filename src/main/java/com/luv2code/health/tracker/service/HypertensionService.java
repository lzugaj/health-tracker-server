package com.luv2code.health.tracker.service;

import com.luv2code.health.tracker.domain.Hypertension;
import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.repository.HypertensionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

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
                        String.format("Cannot find searched Hypertension by given id. [id=%d]", id)
                ));
    }

    public Set<Hypertension> findAllForCurrentUser() {
        return new HashSet<>(userService.findCurrentLoggedInUser().getHypertensions());
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

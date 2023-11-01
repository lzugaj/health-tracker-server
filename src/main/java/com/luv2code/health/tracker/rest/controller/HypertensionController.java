package com.luv2code.health.tracker.rest.controller;

import com.luv2code.health.tracker.domain.Hypertension;
import com.luv2code.health.tracker.rest.dto.BodyMassIndexDTO;
import com.luv2code.health.tracker.rest.dto.HypertensionDTO;
import com.luv2code.health.tracker.rest.mapper.HypertensionMapper;
import com.luv2code.health.tracker.service.HypertensionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hypertensions")
@Tag(name = "Hypertension", description = "Hypertension API")
//@SecurityRequirement(name = "bearer-authentication")
public class HypertensionController {
    
    private final HypertensionService hypertensionService;
    private final HypertensionMapper hypertensionMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Create a hypertension",
            description = "Create a Hypertension object by sending object with systolic and diastolic fields in the request body."
    )
    public void save(@Valid @RequestBody HypertensionDTO dto) {
        Hypertension hypertension = hypertensionMapper.toEntity(dto);
        hypertensionService.save(hypertension);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get a hypertension by id",
            description = "Get a Hypertension object by specifying its id. The response is Hypertension object with id, systolic and diastolic fields in the response body."
    )
    public HypertensionDTO findById(@PathVariable Long id) {
        Hypertension searchedHypertension = hypertensionService.findById(id);
        return hypertensionMapper.toDto(searchedHypertension);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get a set of hypertensions",
            description = "Get a set of Hypertension objects. The response is set of Hypertension objects with id, systolic and diastolic fields in the response body."
    )
    public Set<HypertensionDTO> findAllForCurrentUser() {
        return hypertensionService.findAllForCurrentUser()
                .stream()
                .map(hypertensionMapper::toDto)
                .collect(Collectors.toSet());
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Update a hypertension by id",
            description = "Update a Hypertension object by specifying its id and sending object with systolic and diastolic fields in the request body."
    )
    public void update(@PathVariable Long id, @RequestBody HypertensionDTO dto) {
        Hypertension hypertension = hypertensionMapper.toEntity(dto);
        hypertensionService.update(id, hypertension);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Delete a hypertension by id",
            description = "Delete a Hypertension object by specifying its id."
    )
    public void delete(@PathVariable Long id) {
        hypertensionService.delete(id);
    }
}

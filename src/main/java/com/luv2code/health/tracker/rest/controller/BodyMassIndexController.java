package com.luv2code.health.tracker.rest.controller;

import com.luv2code.health.tracker.domain.body_mass_index.BodyMassIndex;
import com.luv2code.health.tracker.rest.dto.BodyMassIndexDTO;
import com.luv2code.health.tracker.rest.mapper.BodyMassIndexMapper;
import com.luv2code.health.tracker.service.BodyMassIndexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/body-mass-indices")
@Tag(name = "Body Mass Index", description = "Body mass index API")
@SecurityRequirement(name = "google-oauth2")
public class BodyMassIndexController {

    private final BodyMassIndexService bodyMassIndexService;
    private final BodyMassIndexMapper bodyMassIndexMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Create a body mass index",
            description = "Create a BodyMassIndex object by sending object with height, weight, age and gender in the request body.")
    public void save(@Valid @RequestBody BodyMassIndexDTO dto) {
        BodyMassIndex bodyMassIndex = bodyMassIndexMapper.toEntity(dto);
        bodyMassIndexService.save(bodyMassIndex);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get a body mass index by id",
            description = "Get a BodyMassIndex object by specifying its id. The response is BodyMassIndex object with id, height, weight, age, gender and bmi value.")
    public BodyMassIndexDTO findById(@PathVariable Long id) {
        BodyMassIndex searchedBodyMassIndex = bodyMassIndexService.findById(id);
        return bodyMassIndexMapper.toDto(searchedBodyMassIndex);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Get a set of body mass indices",
            description = "Get a set of BodyMassIndex objects. The response is set of BodyMassIndex objects with id, height, weight, age, gender and bmi value.")
    public Set<BodyMassIndexDTO> findAllForCurrentUser() {
        return bodyMassIndexService.findAllForCurrentUser()
                .stream()
                .map(bodyMassIndexMapper::toDto)
                .collect(Collectors.toSet());
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Update a body mass index by id",
            description = "Update a BodyMassIndex object by specifying its id and sending object with height, weight, age and gender in the request body.")
    public void update(@PathVariable Long id, @RequestBody BodyMassIndexDTO dto) {
        BodyMassIndex bodyMassIndex = bodyMassIndexMapper.toEntity(dto);
        bodyMassIndexService.update(id, bodyMassIndex);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(
            summary = "Delete a body mass index by id",
            description = "Delete a BodyMassIndex object by specifying its id."
    )
    public void delete(@PathVariable Long id) {
        bodyMassIndexService.delete(id);
    }
}

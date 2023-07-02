package com.luv2code.health.tracker.rest.controller;

import com.luv2code.health.tracker.domain.BodyMassIndex;
import com.luv2code.health.tracker.rest.dto.BodyMassIndexDTO;
import com.luv2code.health.tracker.rest.mapper.BodyMassIndexMapper;
import com.luv2code.health.tracker.service.BodyMassIndexService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/body-mass-index")
public class BodyMassIndexController {

    private final BodyMassIndexService bodyMassIndexService;
    private final BodyMassIndexMapper bodyMassIndexMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    public void save(@Valid @RequestBody BodyMassIndexDTO dto) {
        BodyMassIndex bodyMassIndex = bodyMassIndexMapper.toEntity(dto);
        bodyMassIndexService.save(bodyMassIndex);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public @ResponseBody BodyMassIndexDTO findById(@PathVariable Long id) {
        BodyMassIndex searchedBodyMassIndex = bodyMassIndexService.findById(id);
        return bodyMassIndexMapper.toDto(searchedBodyMassIndex);
    }

    @GetMapping
    @ResponseStatus(OK)
    public @ResponseBody List<BodyMassIndexDTO> findAll() {
        return bodyMassIndexService.findAll().stream()
                .map(bodyMassIndexMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody BodyMassIndexDTO dto) {
        bodyMassIndexService.update(id, bodyMassIndexMapper.toEntity(dto));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bodyMassIndexService.delete(id);
    }
}

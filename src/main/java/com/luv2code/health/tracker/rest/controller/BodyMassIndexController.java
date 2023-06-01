package com.luv2code.health.tracker.rest.controller;

import com.luv2code.health.tracker.domain.BodyMassIndex;
import com.luv2code.health.tracker.rest.dto.BodyMassIndexDTO;
import com.luv2code.health.tracker.rest.mapper.BodyMassIndexMapper;
import com.luv2code.health.tracker.service.BodyMassIndexService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/body-mass-index")
public class BodyMassIndexController {

    private final BodyMassIndexService bodyMassIndexService;

    private final BodyMassIndexMapper bodyMassIndexMapper;

    public BodyMassIndexController(BodyMassIndexService bodyMassIndexService,
                                   BodyMassIndexMapper bodyMassIndexMapper) {
        this.bodyMassIndexService = bodyMassIndexService;
        this.bodyMassIndexMapper = bodyMassIndexMapper;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> save(@Valid @RequestBody BodyMassIndexDTO dto) {
        BodyMassIndex bodyMassIndex = bodyMassIndexMapper.toEntity(dto);
        bodyMassIndexService.save(bodyMassIndex);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BodyMassIndexDTO> findById(@PathVariable Long id) {
        BodyMassIndex searchedBodyMassIndex = bodyMassIndexService.findById(id);
        BodyMassIndexDTO dto = bodyMassIndexMapper.toDto(searchedBodyMassIndex);
        return ResponseEntity.status(OK).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<BodyMassIndexDTO>> findAll() {
        List<BodyMassIndexDTO> dtos = bodyMassIndexService.findAll().stream()
                .map(bodyMassIndexMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(OK).body(dtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable Long id, @RequestBody BodyMassIndexDTO dto) {
        BodyMassIndex searchedBodyMassIndex = bodyMassIndexService.findById(id);
        searchedBodyMassIndex.update(dto);
        bodyMassIndexService.update(searchedBodyMassIndex);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        BodyMassIndex searchedBodyMassIndex = bodyMassIndexService.findById(id);
        bodyMassIndexService.delete(searchedBodyMassIndex);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}

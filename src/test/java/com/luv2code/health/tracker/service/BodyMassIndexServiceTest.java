package com.luv2code.health.tracker.service;

import com.luv2code.health.tracker.domain.BodyMassIndex;
import com.luv2code.health.tracker.repository.BodyMassIndexRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.luv2code.health.tracker.data.BodyMassIndexTestData.createBodyMassIndex;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BodyMassIndexServiceTest {

    @Mock
    private BodyMassIndexRepository bodyMassIndexRepository;

    @InjectMocks
    public BodyMassIndexService bodyMassIndexService;

    @Test
    public void givenBodyMassIndex_whenSave_thenVerify() {
        BodyMassIndex bodyMassIndex = createBodyMassIndex(180.0, 75.4);

        bodyMassIndexService.save(bodyMassIndex);

        verify(bodyMassIndexRepository, times(1)).save(bodyMassIndex);
    }

    @Test
    public void givenBodyMassIndex_whenFindById_thenThrowEntityNotFoundException() {
        Long notFound = 2L;

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bodyMassIndexService.findById(notFound));

        String expectedMessage = String.format("Cannot find searched Body Mass Index. [id=%d]", notFound);
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void givenBodyMassIndex_whenFindById_thenReturnSearchedBodyMassIndex() {
        BodyMassIndex bodyMassIndex = createBodyMassIndex(1L, 180.0, 75.4, 23.27, LocalDateTime.now(), LocalDateTime.now());

        when(bodyMassIndexRepository.findById(bodyMassIndex.getId())).thenReturn(Optional.of(bodyMassIndex));

        BodyMassIndex searchedBodyMassIndex = bodyMassIndexService.findById(bodyMassIndex.getId());

        assertNotNull(searchedBodyMassIndex);
        assertEquals(1, searchedBodyMassIndex.getId());
        assertEquals(180.0, searchedBodyMassIndex.getHeight());
        assertEquals(75.4, searchedBodyMassIndex.getWeight());
        assertEquals(23.27, searchedBodyMassIndex.getValue());
    }

    @Test
    public void givenBodyMassIndex_whenFindAll_thenReturnListOfSearchedBodyMassIndexs() {
        BodyMassIndex firstBodyMassIndex = createBodyMassIndex(1L, 180.0, 75.4, 23.27, LocalDateTime.now(), LocalDateTime.now());
        BodyMassIndex secondBodyMassIndex = createBodyMassIndex(2L, 180.0, 75.4, 23.27, LocalDateTime.now(), LocalDateTime.now());
        BodyMassIndex thirdBodyMassIndex = createBodyMassIndex(3L, 180.0, 75.4, 23.27, LocalDateTime.now(), LocalDateTime.now());
        List<BodyMassIndex> bodyMassIndices = List.of(firstBodyMassIndex, secondBodyMassIndex, thirdBodyMassIndex);

        when(bodyMassIndexRepository.findAll()).thenReturn(bodyMassIndices);

        List<BodyMassIndex> searchedBodyMassIndices = bodyMassIndexService.findAll();

        assertNotNull(searchedBodyMassIndices);
        assertEquals(3, searchedBodyMassIndices.size());
        assertEquals(bodyMassIndices, searchedBodyMassIndices);
    }

    @Test
    public void givenBodyMassIndex_whenUpdate_ThenVerify() {
        BodyMassIndex updatedBodyMassIndex = createBodyMassIndex(1L, 180.0, 75.4, 23.27, LocalDateTime.now(), LocalDateTime.now());

        bodyMassIndexService.update(updatedBodyMassIndex);

        verify(bodyMassIndexRepository, times(1)).save(updatedBodyMassIndex);
    }

    @Test
    public void givenBodyMassIndex_whenDelete_ThenVerify() {
        BodyMassIndex updatedBodyMassIndex = createBodyMassIndex(1L, 180.0, 75.4, 23.27, LocalDateTime.now(), LocalDateTime.now());

        bodyMassIndexService.delete(updatedBodyMassIndex);

        verify(bodyMassIndexRepository, times(1)).delete(updatedBodyMassIndex);
    }
}

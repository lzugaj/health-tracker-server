package com.luv2code.health.tracker.mapper;

import com.luv2code.health.tracker.domain.body_mass_index.BodyMassIndex;
import com.luv2code.health.tracker.rest.dto.BodyMassIndexDTO;
import com.luv2code.health.tracker.rest.mapper.BodyMassIndexMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.luv2code.health.tracker.data.AdultBodyMassIndexTestData.createAdultBodyMassIndex;
import static com.luv2code.health.tracker.domain.enums.Gender.FEMALE;
import static com.luv2code.health.tracker.domain.enums.Gender.MALE;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BodyMassIndexMapperTest {

    @InjectMocks
    public BodyMassIndexMapper bodyMassIndexMapper;

    @Test
    public void givenEmptyBodyMassIndexRequestDTO_whenToEntity_thenThrowsException() {
        Exception exception = assertThrows(
                NullPointerException.class,
                () -> bodyMassIndexMapper.toEntity(null));

        String expectedMessage = "Given dto is null.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void givenBodyMassIndexRequestDTO_whenToEntity_thenReturnYouthBodyMassIndex() {
        BodyMassIndexDTO dto = BodyMassIndexDTO.builder()
                .height(175.34)
                .weight(45.55)
                .age(18)
                .gender(FEMALE.name())
                .build();

        BodyMassIndex entity = bodyMassIndexMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(14.82, entity.getValue());
    }

    @Test
    public void givenBodyMassIndexRequestDTO_whenToEntity_thenReturnAdultBodyMassIndex() {
        BodyMassIndexDTO dto = BodyMassIndexDTO.builder()
                .height(175.34)
                .weight(45.55)
                .age(23)
                .gender(FEMALE.name())
                .build();

        BodyMassIndex entity = bodyMassIndexMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(14.82, entity.getValue());
    }

    @Test
    public void givenEmptyBodyMassIndex_whenToEntity_thenThrowsException() {
        Exception exception = assertThrows(
                NullPointerException.class,
                () -> bodyMassIndexMapper.toDto(null));

        String expectedMessage = "Given entity is null.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void givenBodyMassIndex_whenToEntity_thenReturnBodyMassIndexDTO() {
        BodyMassIndex entity = createAdultBodyMassIndex(193.50, 102.00, 23, MALE);

        BodyMassIndexDTO dto = bodyMassIndexMapper.toDto(entity);

        assertNotNull(dto);
        assertEquals(27.24, entity.getValue());
    }
}

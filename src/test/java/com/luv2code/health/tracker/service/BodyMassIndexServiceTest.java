package com.luv2code.health.tracker.service;

import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.domain.body_mass_index.BodyMassIndex;
import com.luv2code.health.tracker.repository.BodyMassIndexRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static com.luv2code.health.tracker.data.AdultBodyMassIndexTestData.createAdultBodyMassIndex;
import static com.luv2code.health.tracker.data.UserTestData.createUser;
import static com.luv2code.health.tracker.domain.enums.Gender.MALE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BodyMassIndexServiceTest {

    @Mock
    private BodyMassIndexRepository bodyMassIndexRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    public BodyMassIndexService bodyMassIndexService;

    @Test
    public void givenBodyMassIndex_whenSave_thenAddToUserList() {
        BodyMassIndex bodyMassIndex = createAdultBodyMassIndex(180.0, 75.4, 28, MALE);
        User currentUser = createUser("John", "Doe", "john.doe@gmail.com");

        when(userService.findCurrentLoggedInUser()).thenReturn(currentUser);

        bodyMassIndexService.save(bodyMassIndex);

        assertNotNull(currentUser.getBodyMassIndices());
        assertEquals(1, currentUser.getBodyMassIndices().size());
    }

    @Test
    public void givenBodyMassIndex_whenFindById_thenThrowEntityNotFoundException() {
        Long notFound = 2L;

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bodyMassIndexService.findById(notFound));

        String expectedMessage = String.format("Cannot find searched Body Mass Index by given id. [id=%d]", notFound);
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void givenBodyMassIndex_whenFindById_thenReturnSearchedBodyMassIndex() {
        BodyMassIndex bodyMassIndex = createAdultBodyMassIndex(180.0, 75.4, 23, MALE);

        when(bodyMassIndexRepository.findById(bodyMassIndex.getId())).thenReturn(Optional.of(bodyMassIndex));

        BodyMassIndex searchedBodyMassIndex = bodyMassIndexService.findById(bodyMassIndex.getId());

        assertNotNull(searchedBodyMassIndex);
        assertEquals(searchedBodyMassIndex, bodyMassIndex);
    }

    @Test
    public void givenBodyMassIndex_whenFindAllForCurrentUser_thenReturnListOfSearchedBodyMassIndexes() {
        User currentUser = createUser("John", "Doe", "john.doe@gmail.com");
        BodyMassIndex firstBodyMassIndex = createAdultBodyMassIndex(180.0, 75.4, 34, MALE);
        BodyMassIndex secondBodyMassIndex = createAdultBodyMassIndex(180.0, 73.6, 35, MALE);
        BodyMassIndex thirdBodyMassIndex = createAdultBodyMassIndex(180.0, 80.0, 36, MALE);

        currentUser.addBodyMassIndex(firstBodyMassIndex);
        currentUser.addBodyMassIndex(secondBodyMassIndex);
        currentUser.addBodyMassIndex(thirdBodyMassIndex);

        when(userService.findCurrentLoggedInUser()).thenReturn(currentUser);

        Set<BodyMassIndex> searchedBodyMassIndices = bodyMassIndexService.findAllForCurrentUser();

        assertNotNull(searchedBodyMassIndices);
        assertEquals(3, searchedBodyMassIndices.size());
    }

    @Test
    public void givenBodyMassIndex_whenUpdate_thenVerify() {
        BodyMassIndex updatedBodyMassIndex = createAdultBodyMassIndex(180.0, 75.4, 23, MALE);

        when(bodyMassIndexRepository.findById(updatedBodyMassIndex.getId())).thenReturn(Optional.of(updatedBodyMassIndex));

        bodyMassIndexService.update(updatedBodyMassIndex.getId(), updatedBodyMassIndex);

        assertNotNull(updatedBodyMassIndex);
    }

    @Test
    public void givenBodyMassIndex_whenDelete_thenVerify() {
        Long bodyMassIndexId = 1L;
        User currentUser = createUser("John", "Doe", "john.doe@gmail.com");
        BodyMassIndex deletedBodyMassIndex = createAdultBodyMassIndex(180.0, 75.4, 56, MALE);
        currentUser.addBodyMassIndex(deletedBodyMassIndex);

        when(bodyMassIndexRepository.findById(bodyMassIndexId)).thenReturn(Optional.of(deletedBodyMassIndex));
        when(userService.findCurrentLoggedInUser()).thenReturn(currentUser);

        bodyMassIndexService.delete(bodyMassIndexId);

        assertEquals(0, currentUser.getBodyMassIndices().size());
    }
}

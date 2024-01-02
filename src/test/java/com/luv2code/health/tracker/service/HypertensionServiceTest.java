package com.luv2code.health.tracker.service;

import com.luv2code.health.tracker.domain.Hypertension;
import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.repository.HypertensionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static com.luv2code.health.tracker.data.HypertensionData.createHypertension;
import static com.luv2code.health.tracker.data.UserTestData.createUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HypertensionServiceTest {

    @Mock
    private HypertensionRepository hypertensionRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    public HypertensionService hypertensionService;

    @Test
    public void givenHypertension_whenSave_thenAddToUserList() {
        Hypertension hypertension = createHypertension(120, 80);
        User currentUser = createUser("John", "Doe", "john.doe@gmail.com");

        when(userService.findCurrentLoggedInUser()).thenReturn(currentUser);

        hypertensionService.save(hypertension);

        assertNotNull(currentUser.getBodyMassIndices());
        assertEquals(1, currentUser.getHypertensions().size());
    }

    @Test
    public void givenHypertension_whenFindById_thenThrowEntityNotFoundException() {
        Long notFound = 2L;

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> hypertensionService.findById(notFound));

        String expectedMessage = String.format("Cannot find searched Hypertension by given id. [id=%d]", notFound);
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void givenHypertension_whenFindById_thenReturnSearchedBodyMassIndex() {
        Hypertension hypertension = createHypertension(120, 80);

        when(hypertensionRepository.findById(hypertension.getId())).thenReturn(Optional.of(hypertension));

        Hypertension searchedHypertension = hypertensionService.findById(hypertension.getId());

        assertNotNull(searchedHypertension);
        assertEquals(searchedHypertension, hypertension);
    }

    @Test
    public void givenHypertension_whenFindAllForCurrentUser_thenReturnListOfSearchedHypertension() {
        User currentUser = createUser("John", "Doe", "john.doe@gmail.com");
        Hypertension firstHypertension = createHypertension(120, 80);
        Hypertension secondHypertension = createHypertension(130, 90);
        Hypertension thirdHypertension = createHypertension(110, 75);

        currentUser.addHypertension(firstHypertension);
        currentUser.addHypertension(secondHypertension);
        currentUser.addHypertension(thirdHypertension);

        when(userService.findCurrentLoggedInUser()).thenReturn(currentUser);

        Set<Hypertension> searchedHypertensions = hypertensionService.findAllForCurrentUser();

        assertNotNull(searchedHypertensions);
        assertEquals(3, searchedHypertensions.size());
    }

    @Test
    public void givenHypertension_whenUpdate_thenVerify() {
        Hypertension updatedHypertension = createHypertension(130, 90);

        when(hypertensionRepository.findById(updatedHypertension.getId())).thenReturn(Optional.of(updatedHypertension));

        hypertensionService.update(updatedHypertension.getId(), updatedHypertension);

        assertNotNull(updatedHypertension);
    }

    @Test
    public void givenHypertension_whenDelete_thenVerify() {
        Long bodyMassIndexId = 1L;
        User currentUser = createUser("John", "Doe", "john.doe@gmail.com");
        Hypertension deletedHypertension = createHypertension(130, 90);
        currentUser.addHypertension(deletedHypertension);

        when(hypertensionRepository.findById(bodyMassIndexId)).thenReturn(Optional.of(deletedHypertension));
        when(userService.findCurrentLoggedInUser()).thenReturn(currentUser);

        hypertensionService.delete(bodyMassIndexId);

        assertEquals(0, currentUser.getBodyMassIndices().size());
    }
}

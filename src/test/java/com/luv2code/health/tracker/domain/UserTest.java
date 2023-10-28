package com.luv2code.health.tracker.domain;

import com.luv2code.health.tracker.data.AdultBodyMassIndexTestData;
import com.luv2code.health.tracker.data.HypertensionData;
import com.luv2code.health.tracker.domain.body_mass_index.BodyMassIndex;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;

import static com.luv2code.health.tracker.data.HypertensionData.createHypertension;
import static com.luv2code.health.tracker.data.UserTestData.createUser;
import static com.luv2code.health.tracker.domain.enums.Gender.FEMALE;
import static com.luv2code.health.tracker.domain.enums.Role.USER;
import static com.luv2code.health.tracker.util.ClockUtil.resetClock;
import static com.luv2code.health.tracker.util.ClockUtil.useFixedClockAt;
import static java.time.Instant.parse;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @BeforeEach
    public void setUp() {
        // Set a fixed clock for testing
        Instant fixedInstant = parse("2025-02-24T08:23:45Z");
        useFixedClockAt(fixedInstant);
    }

    @AfterEach
    public void tearDown() {
        // Reset the clock to the system default after the test
        resetClock();
    }

    @Test
    public void givenNullValueForLastName_whenBuildingEntity_thenThrowNullPointerException() {
        Exception exception = assertThrows(
                NullPointerException.class,
                () -> createUser("John", null, "john.doe@gmail.com"));

        String expectedMessage = "Last name must be supplied.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void givenValidValues_whenBuildingEntity_thenCreateIt() {
        User user = createUser("John", "Wick", "john.wick4@gmail.com");

        assertNotNull(user);
        assertEquals("John", user.getFirstName());
        assertEquals("Wick", user.getLastName());
        assertEquals("john.wick4@gmail.com", user.getEmail());
        assertEquals(USER, user.getRole());
        assertEquals(LocalDateTime.parse("2025-02-24T08:23:45"), user.getCreatedAt());
        assertEquals(LocalDateTime.parse("2025-02-24T08:23:45"), user.getModifiedAt());
        assertEquals(0, user.getBodyMassIndices().size());
    }

    @Test
    public void givenUser_whenGetAuthorities_thenReturnCollectionOfGrantedAuthority() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertNotNull(authorities);
        assertEquals("ROLE_USER", authorities.stream().findFirst().get().getAuthority());
    }

    @Test
    public void givenUser_whenGetPassword_thenReturnEmptyPasswordString() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");

        String password = user.getPassword();

        assertEquals("", password);
    }

    @Test
    public void givenUser_whenGetUsername_thenReturnUserEmail() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");

        String email = user.getUsername();

        assertNotNull(email);
        assertEquals("john.doe@gmail.com", email);
    }

    @Test
    public void givenUser_whenIsAccountNonExpired_thenReturnTrue() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");

        boolean accountNonExpired = user.isAccountNonExpired();

        assertTrue(accountNonExpired);
    }

    @Test
    public void givenUser_whenIsAccountNonLocked_thenReturnTrue() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");

        boolean accountNonLocked = user.isAccountNonLocked();

        assertTrue(accountNonLocked);
    }

    @Test
    public void givenUser_whenIsCredentialsNonExpired_thenReturnTrue() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");

        boolean accountNonExpired = user.isCredentialsNonExpired();

        assertTrue(accountNonExpired);
    }

    @Test
    public void givenUser_whenIsEnabled_thenReturnTrue() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");

        boolean enabled = user.isEnabled();

        assertTrue(enabled);
    }

    @Test
    public void givenUser_whenUpdate_thenUpdateAndValidate() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");
        User updatedUser = createUser("John", "Wick", "john.wick4@gmail.com");

        user.update(updatedUser);

        assertEquals("John", user.getFirstName());
        assertEquals("Wick", user.getLastName());
        assertEquals("john.wick4@gmail.com", user.getEmail());
        assertEquals(LocalDateTime.parse("2025-02-24T08:23:45"), user.getModifiedAt());
    }

    @Test
    public void givenBodyMassIndex_whenAddBodyMassIndex_thenAddItToTheList() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");
        BodyMassIndex bodyMassIndex = AdultBodyMassIndexTestData.createAdultBodyMassIndex(167.0, 54.2, 31, FEMALE);

        user.addBodyMassIndex(bodyMassIndex);

        assertNotNull(user.getBodyMassIndices());
        assertEquals(1, user.getBodyMassIndices().size());
    }

    @Test
    public void givenBodyMassIndex_whenRemoveBodyMassIndex_thenRemoveItFromTheList() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");
        BodyMassIndex firstBodyMassIndex = AdultBodyMassIndexTestData.createAdultBodyMassIndex(167.0, 54.2, 31, FEMALE);
        BodyMassIndex secondBodyMassIndex = AdultBodyMassIndexTestData.createAdultBodyMassIndex(167.0, 57.5, 32, FEMALE);
        BodyMassIndex thirdBodyMassIndex = AdultBodyMassIndexTestData.createAdultBodyMassIndex(167.0, 52.4, 34, FEMALE);

        user.addBodyMassIndex(firstBodyMassIndex);
        user.addBodyMassIndex(secondBodyMassIndex);
        user.addBodyMassIndex(thirdBodyMassIndex);
        user.removeBodyMassIndex(secondBodyMassIndex);

        assertNotNull(user.getBodyMassIndices());
        assertEquals(2, user.getBodyMassIndices().size());
    }

    @Test
    public void givenHypertension_whenAddHypertension_thenAddItToTheList() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");
        Hypertension hypertension = createHypertension(140, 90);

        user.addHypertension(hypertension);

        assertNotNull(user.getHypertensions());
        assertEquals(1, user.getHypertensions().size());
    }

    @Test
    public void givenHypertension_whenRemoveHypertension_thenRemoveItFromTheList() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");
        Hypertension firstHypertension = createHypertension(140, 90);
        Hypertension secondHypertension = createHypertension(120, 80);
        Hypertension thirdHypertension = createHypertension(160, 110);

        user.addHypertension(firstHypertension);
        user.addHypertension(secondHypertension);
        user.addHypertension(thirdHypertension);
        user.removeHypertension(thirdHypertension);

        assertNotNull(user.getHypertensions());
        assertEquals(2, user.getHypertensions().size());
    }
}

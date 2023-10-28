package com.luv2code.health.tracker.service;

import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static com.luv2code.health.tracker.data.UserTestData.createUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void nonExistingEmail_loadUserByUsername_throwsEntityNotFoundException() {
        String nonExistingEmail = "test1@gmail.com";

        when(userRepository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(nonExistingEmail));

        String expectedMessage = String.format("Cannot find searched User by given email. [email=%s]", nonExistingEmail);
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void existingEmail_loadUserByUsername_returnUserDetails() {
        User searchedUser = createUser("John", "Doe", "john.doe@gmail.com");

        when(userRepository.findByEmail(searchedUser.getEmail())).thenReturn(Optional.of(searchedUser));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(searchedUser.getEmail());

        assertNotNull(userDetails);
        assertEquals(searchedUser.getEmail(), userDetails.getUsername());
    }
}

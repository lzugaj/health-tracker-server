package com.luv2code.health.tracker.service;

import com.luv2code.health.tracker.exception.UserNotAuthenticatedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private AnonymousAuthenticationToken anonymousAuthenticationToken;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setup() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void getCurrentUser_throwsUserNotAuthenticatedException() {
        when(securityContext.getAuthentication()).thenReturn(anonymousAuthenticationToken);

        Exception exception = assertThrows(
                UserNotAuthenticatedException.class,
                () -> authenticationService.getCurrentUser());

        String expectedMessage = String.format("Request is rejected because user is not authenticated. [email=%s]", "null");
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void getCurrentUser_returnUserEmail() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user@test.net");

        Authentication authenticatedUser = authenticationService.getCurrentUser();

        assertEquals("user@test.net", authenticatedUser.getName());
    }
}

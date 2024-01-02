package com.luv2code.health.tracker.auditing;

import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationAuditAwareTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private Authentication authentication;

    @Mock
    private User currentUser;

    @InjectMocks
    private ApplicationAuditAware applicationAuditAware;

    @Test
    public void getCurrentAuditor() {
        Long currentUserId = 1L;

        when(authenticationService.getCurrentUser()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(currentUser);
        when(currentUser.getId()).thenReturn(currentUserId);

        Optional<Long> currentAuditor = applicationAuditAware.getCurrentAuditor();

        assertNotNull(currentAuditor);
        assertEquals(Optional.of(currentUserId), currentAuditor);
    }
}

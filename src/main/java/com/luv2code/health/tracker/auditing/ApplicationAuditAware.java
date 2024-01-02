package com.luv2code.health.tracker.auditing;

import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class ApplicationAuditAware implements AuditorAware<Long> {

    private final AuthenticationService authenticationService;

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication currentUser = authenticationService.getCurrentUser();
        User userPrincipal = (User) currentUser.getPrincipal();
        return ofNullable(userPrincipal.getId());
    }
}

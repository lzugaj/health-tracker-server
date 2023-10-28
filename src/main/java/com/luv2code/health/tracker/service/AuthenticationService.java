package com.luv2code.health.tracker.service;

import com.luv2code.health.tracker.exception.UserNotAuthenticatedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@Transactional(readOnly = true)
public class AuthenticationService {

    public Authentication getCurrentUser() {
        Authentication currentAuthenticatedUser = getContext().getAuthentication();
        if (currentAuthenticatedUser instanceof AnonymousAuthenticationToken) {
            throw new UserNotAuthenticatedException(
                    String.format("Request is rejected because user is not authenticated. [email=%s]",
                            currentAuthenticatedUser.getName())
            );
        }

        return currentAuthenticatedUser;
    }
}

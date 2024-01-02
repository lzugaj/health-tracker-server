package com.luv2code.health.tracker.service;

import com.luv2code.health.tracker.domain.RefreshToken;
import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.properties.TokenProperties;
import com.luv2code.health.tracker.repository.UserRepository;
import com.luv2code.health.tracker.rest.dto.AuthDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.luv2code.health.tracker.security.TokenProvider.generateToken;
import static java.lang.String.valueOf;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final TokenProperties tokenProperties;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationService authenticationService;

    @Transactional
    public AuthDTO persistUserWithAuthProcessing(User user) {
        Optional<User> searchedUser = userRepository.findByEmail(user.getEmail());
        return searchedUser.isPresent() ?
                updateExistingUser(searchedUser.get()) :
                createNewUser(user);
    }

    private AuthDTO updateExistingUser(User existingUser) {
        existingUser.update(existingUser);
        return refreshTokenService.findByUser(existingUser);
    }

    private AuthDTO createNewUser(User newUser) {
        userRepository.persist(newUser);

        String accessToken = generateToken(newUser, tokenProperties.getSecret());
        RefreshToken refreshToken = refreshTokenService.create(newUser);

        return AuthDTO.builder()
                .accessToken(accessToken)
                .refreshToken(valueOf(refreshToken.getToken()))
                .userId(newUser.getId())
                .role(newUser.getRole().name())
                .build();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Cannot found searched User by given email. [email=%s]", email)
                ));
    }

    public User findCurrentLoggedInUser() {
        Authentication currentUser = authenticationService.getCurrentUser();
        return findByEmail(currentUser.getName());
    }
}

package com.luv2code.health.tracker.service;

import com.luv2code.health.tracker.domain.RefreshToken;
import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.properties.TokenProperties;
import com.luv2code.health.tracker.repository.UserRepository;
import com.luv2code.health.tracker.rest.dto.AuthDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.UUID;

import static com.luv2code.health.tracker.data.RefreshTokenData.createRefreshToken;
import static com.luv2code.health.tracker.data.UserTestData.createUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenProperties tokenProperties;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserService userService;

    private static final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XSwic3ViIjoibHVrYS56dWdhakBzaWdpdC5ociIsImlhdCI6MTY4ODY0OTI4NywiZXhwIjoxNjg4NjUwMTg3fQ.-v0aOH_7zJiksy5wd7yCaiUqiB3gH2M730O8e4qrNyw";
    private static final String USER = "USER";
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    @Test
    public void user_updateExistingUser_returnAuthDTO() {
        User searchedUser = createUser("John", "Doe", "john.doe@gmail.com");
        AuthDTO authDTO = AuthDTO.builder()
                .accessToken(ACCESS_TOKEN)
                .refreshToken(String.valueOf(UUID.randomUUID()))
                .userId(null)
                .role(USER)
                .build();

        when(userRepository.findByEmail(searchedUser.getEmail())).thenReturn(Optional.of(searchedUser));
        when(refreshTokenService.findByUser(searchedUser)).thenReturn(authDTO);

        AuthDTO buildedAuthDTO = userService.persistUserWithAuthProcessing(searchedUser);

        assertNotNull(buildedAuthDTO);
        assertEquals(authDTO, buildedAuthDTO);
    }

    @Test
    public void user_createNewUser_returnAuthDTO() {
        User searchedUser = createUser("John", "Doe", "john.doe@gmail.com");
        RefreshToken refreshToken = createRefreshToken(searchedUser);

        when(userRepository.findByEmail(searchedUser.getEmail())).thenReturn(Optional.empty());
        when(tokenProperties.getSecret()).thenReturn(SECRET_KEY);
        when(refreshTokenService.create(searchedUser)).thenReturn(refreshToken);

        AuthDTO buildedAuthDTO = userService.persistUserWithAuthProcessing(searchedUser);

        assertNotNull(buildedAuthDTO);
        assertEquals(refreshToken.getToken(), UUID.fromString(buildedAuthDTO.refreshToken()));
        assertEquals(searchedUser.getRole().name(), buildedAuthDTO.role());

        verify(userRepository, times(1)).persist(searchedUser);
    }

    @Test
    public void userEmail_findByEmail_throwsEntityNotFoundException() {
        String notFoundUserEmail = "john.doe@gmail.com";

        when(userRepository.findByEmail(notFoundUserEmail)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> userService.findByEmail(notFoundUserEmail));

        String expectedMessage = String.format("Cannot found searched User by given email. [email=%s]", notFoundUserEmail);
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void userEmail_findByEmail_returnSearchedUser() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User searchedUser = userService.findByEmail(user.getEmail());

        assertNotNull(searchedUser);
        assertEquals(user, searchedUser);
    }

    @Test
    public void findCurrentLoggedInUser_returnSearchedUser() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());

        when(authenticationService.getCurrentUser()).thenReturn(authentication);
        when(userRepository.findByEmail(authentication.getName())).thenReturn(Optional.of(user));

        User searchedUser = userService.findCurrentLoggedInUser();

        assertNotNull(searchedUser);
        assertEquals(user, searchedUser);
    }
}

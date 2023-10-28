package com.luv2code.health.tracker.security;

import com.luv2code.health.tracker.domain.User;
import org.junit.jupiter.api.Test;

import static com.luv2code.health.tracker.data.JwtTokenData.generateToken;
import static com.luv2code.health.tracker.data.UserTestData.createUser;
import static com.luv2code.health.tracker.domain.enums.Role.USER;
import static com.luv2code.health.tracker.security.TokenProvider.extractEmail;
import static com.luv2code.health.tracker.security.TokenProvider.generateToken;
import static com.luv2code.health.tracker.security.TokenProvider.isTokenValid;
import static org.junit.jupiter.api.Assertions.*;

public class TokenProviderTest {

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    @Test
    public void tokenSecretKeyAndUserDetails_isTokenValid_returnTrue() {
        User user = createUser("Ivan", "Horvat", "ivan.horvat@gmail.com");

        String jwtToken = generateToken(user);

        boolean itIsValid = isTokenValid(jwtToken, SECRET_KEY, user);

        assertTrue(itIsValid);
    }

    @Test
    public void tokenSecretKeyAndUserDetails_isTokenValid_returnFalse() {
        User user = createUser("Ivan", "Horvat", "ivan.horvat@gmail.com");

        String jwtToken = generateToken(user);

        User updatedUser = createUser("Ivan", "Horvat", "horvat.ivan@gmail.com");
        user.update(updatedUser);

        boolean itIsNotValid = isTokenValid(jwtToken, SECRET_KEY, user);

        assertFalse(itIsNotValid);
    }

    @Test
    public void tokenSecretKey_extractEmail_returnExtractedEmail() {
        User user = createUser("Ivan", "Horvat", "ivan.horvat@gmail.com");

        String jwtToken = generateToken(user);

        String extractedEmail = extractEmail(jwtToken, SECRET_KEY);

        assertEquals(user.getEmail(), extractedEmail);
    }

    @Test
    public void userDetailsAndSecretKey_generateToken_returnCreatedToken() {
        User user = createUser("Ivan", "Horvat", "ivan.horvat@gmail.com");

        String generatedToken = generateToken(user, SECRET_KEY);

        assertNotNull(generatedToken);
    }
}

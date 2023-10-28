package com.luv2code.health.tracker.security;

import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.exception.UserAuthenticationException;
import com.luv2code.health.tracker.properties.TokenProperties;
import com.luv2code.health.tracker.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;

import static com.luv2code.health.tracker.data.JwtTokenData.generateToken;
import static com.luv2code.health.tracker.data.UserTestData.createUser;
import static com.luv2code.health.tracker.domain.enums.Role.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenAuthenticationFilterTest {

    private static final String LOGIN_ENDPOINT = "/login";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    @Mock
    private TokenProperties tokenProperties;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Test
    void doFilterInternal_ignoredPath() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath(LOGIN_ENDPOINT);

        MockHttpServletResponse response = new MockHttpServletResponse();

        tokenAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_validToken() throws ServletException, IOException {
        User user = createUser("Ivan", "Horvat", "ivan.horvat@gmail.com");
        String jwtToken = generateToken(user);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(AUTHORIZATION, BEARER_TOKEN_PREFIX + jwtToken);

        MockHttpServletResponse response = new MockHttpServletResponse();

        when(tokenProperties.getSecret()).thenReturn(SECRET_KEY);
        when(customUserDetailsService.loadUserByUsername("ivan.horvat@gmail.com")).thenReturn(user);

        tokenAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_throwsRuntimeException() {
        User user = createUser("Ivan", "Horvat", "ivan.horvat@gmail.com");
        String jwtToken = generateToken(user);

        MockHttpServletRequest request = new MockHttpServletRequest();

        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader(AUTHORIZATION, BEARER_TOKEN_PREFIX + jwtToken);

        when(tokenProperties.getSecret()).thenReturn(SECRET_KEY);
        when(customUserDetailsService.loadUserByUsername(anyString()))
                .thenThrow(new UsernameNotFoundException("User not found"));

        Exception exception = assertThrows(
                UserAuthenticationException.class,
                () -> tokenAuthenticationFilter.doFilterInternal(request, response, filterChain));

        String expectedMessage = "Cannot set user authentication. User not found";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}

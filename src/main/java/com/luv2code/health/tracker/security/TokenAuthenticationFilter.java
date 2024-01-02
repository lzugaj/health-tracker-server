package com.luv2code.health.tracker.security;


import com.luv2code.health.tracker.exception.UserAuthenticationException;
import com.luv2code.health.tracker.properties.TokenProperties;
import com.luv2code.health.tracker.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.luv2code.health.tracker.constants.SecurityConstants.*;
import static com.luv2code.health.tracker.security.TokenProvider.extractEmail;
import static com.luv2code.health.tracker.security.TokenProvider.isTokenValid;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProperties tokenProperties;
    private final CustomUserDetailsService customUserDetailsService;

    private static final RequestMatcher ignoredPaths = new OrRequestMatcher(
            new AntPathRequestMatcher(LOGIN_ENDPOINT)
            //new AntPathRequestMatcher(ACTUATOR_ENDPOINT)
    );

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            if (ignoredPaths.matches(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            String jwtToken = extractJwtFromRequest(request);
            if (hasText(jwtToken)) {
                String email = extractEmail(jwtToken, tokenProperties.getSecret());
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                if (isTokenValid(jwtToken, tokenProperties.getSecret(), userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Data sent for authentication in could not be parsed.");
        } catch (Exception e) {
            throw new UserAuthenticationException(
                    String.format("Cannot set user authentication. %s", e.getMessage())
            );
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (hasText(bearerToken) && bearerToken.startsWith(BEARER_TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }

        return null;
    }
}

package com.luv2code.health.tracker.data;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.luv2code.health.tracker.constants.SecurityConstants.ACCESS_TOKEN_EXPIRATION_TIME;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.io.Decoders.BASE64;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.lang.System.currentTimeMillis;

public class JwtTokenData {

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        return createToken(claims, userDetails.getUsername());
    }

    public static String generateJohnDoeToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", new SimpleGrantedAuthority("ROLE_USER"));
        return createToken(claims, "john.doe@gmail.com");
    }

    private static String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(currentTimeMillis()))
                .setExpiration(new Date(currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(getSignInKey(), HS256)
                .compact();
    }

    private static Key getSignInKey() {
        byte[] keyBytes = BASE64.decode(SECRET_KEY);
        return hmacShaKeyFor(keyBytes);
    }
}

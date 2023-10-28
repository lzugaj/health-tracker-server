package com.luv2code.health.tracker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.luv2code.health.tracker.constants.SecurityConstants.ACCESS_TOKEN_EXPIRATION_TIME;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.io.Decoders.BASE64;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.lang.System.currentTimeMillis;

public class TokenProvider {
    
    public static boolean isTokenValid(String token, String secretKey, UserDetails userDetails) {
        String username = extractEmail(token, secretKey);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token, secretKey);
    }

    private static boolean isTokenExpired(String token, String secretKey) {
        return extractExpiration(token, secretKey).before(new Date());
    }

    public static String extractEmail(String token, String secretKey) {
        return extractClaim(token, Claims::getSubject, secretKey);
    }

    private static Date extractExpiration(String token, String secretKey) {
        return extractClaim(token, Claims::getExpiration, secretKey);
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String secretKey) {
        Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token, String secretKey) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String generateToken(UserDetails userDetails, String secretKey) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        return createToken(claims, userDetails.getUsername(), secretKey);
    }

    private static String createToken(Map<String, Object> claims, String subject, String secretKey) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(currentTimeMillis()))
                .setExpiration(new Date(currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(getSignInKey(secretKey), HS256)
                .compact();
    }

    private static Key getSignInKey(String secretKey) {
        byte[] keyBytes = BASE64.decode(secretKey);
        return hmacShaKeyFor(keyBytes);
    }
}

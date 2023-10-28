package com.luv2code.health.tracker.mapper;

import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.rest.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Test
    public void user_map_throwsNullPointerException() {
        Exception exception = assertThrows(
                NullPointerException.class,
                () -> userMapper.toEntity(null));

        String expectedMessage = "Given dto is null.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void vacationRequest_map_validate() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Bearer");

        Map<String, Object> claims = new HashMap<>();
        claims.put("given_name", "John");
        claims.put("family_name", "Doe");
        claims.put("email", "john.doe@gmail.com");

        Jwt jwt = new Jwt("test", null, null, headers, claims);

        User mappedUser = userMapper.toEntity(jwt);

        assertNotNull(mappedUser);
        assertEquals("John", mappedUser.getFirstName());
        assertEquals("Doe", mappedUser.getLastName());
        assertEquals("john.doe@gmail.com", mappedUser.getEmail());
    }
}

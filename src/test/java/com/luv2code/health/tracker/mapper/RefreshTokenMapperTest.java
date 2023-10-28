package com.luv2code.health.tracker.mapper;

import com.luv2code.health.tracker.domain.RefreshToken;
import com.luv2code.health.tracker.domain.User;
import com.luv2code.health.tracker.rest.mapper.RefreshTokenMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.luv2code.health.tracker.data.UserTestData.createUser;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenMapperTest {

    @InjectMocks
    private RefreshTokenMapper refreshTokenMapper;

    @Test
    public void user_map_throwsNullPointerException() {
        Exception exception = assertThrows(
                NullPointerException.class,
                () -> refreshTokenMapper.toEntity(null));

        String expectedMessage = "Given entity is null.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void vacationRequest_map_validate() {
        User user = createUser("John", "Doe", "john.doe@gmail.com");

        RefreshToken mappedRefreshToken = refreshTokenMapper.toEntity(user);

        assertNotNull(mappedRefreshToken);
        assertEquals(user, mappedRefreshToken.getUser());
    }
}

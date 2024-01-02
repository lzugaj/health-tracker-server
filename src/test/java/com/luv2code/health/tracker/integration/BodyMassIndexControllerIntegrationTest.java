package com.luv2code.health.tracker.integration;

import com.luv2code.health.tracker.rest.dto.BodyMassIndexDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.luv2code.health.tracker.constants.SecurityConstants.BEARER_TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static reactor.core.publisher.Mono.just;

@Disabled
public class BodyMassIndexControllerIntegrationTest extends TestcontainersInitializer {

    @Autowired
    protected WebTestClient webTestClient;

    @Value("${john.doe.jwt.token}")
    private String johnDoeToken;

    @Test
    @DisplayName("201 - POST /api/v1/body-mass-indices")
    public void bodyMassIndex_save_successfully() {
        BodyMassIndexDTO dto = BodyMassIndexDTO.builder()
                .height(192.00)
                .weight(107.25)
                .build();

        webTestClient.post()
                .uri("/api/v1/body-mass-indices")
                .header(AUTHORIZATION, BEARER_TOKEN_PREFIX + johnDoeToken)
                .body(just(dto), BodyMassIndexDTO.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }
}

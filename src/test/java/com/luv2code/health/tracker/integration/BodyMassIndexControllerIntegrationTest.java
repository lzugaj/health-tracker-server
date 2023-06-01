package com.luv2code.health.tracker.integration;

import com.luv2code.health.tracker.rest.dto.BodyMassIndexDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BodyMassIndexControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @DisplayName("201 - POST /api/v1/body-mass-index")
    public void bodyMassIndex_save_successfully() {
        BodyMassIndexDTO dto = BodyMassIndexDTO.builder()
                .height(192.00)
                .weight(107.25)
                .build();

        webTestClient.post()
                .uri("/api/v1/body-mass-index")
                .body(just(dto), BodyMassIndexDTO.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    @DisplayName("200 - GET /api/v1/body-mass-index/101")
    public void bodyMassIndex_findById_successfully() {
        BodyMassIndexDTO expectedDTO = BodyMassIndexDTO.builder()
                .id(101L)
                .height(173.00)
                .weight(56.00)
                .value(18.2)
                .build();

        webTestClient.get()
                .uri("/api/v1/body-mass-index/{id}", 101)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(BodyMassIndexDTO.class)
                .consumeWith(response -> {
                    BodyMassIndexDTO actualDTO = response.getResponseBody();
                    assertNotNull(actualDTO);
                    assertEquals(expectedDTO.getId(), actualDTO.getId());
                    assertEquals(expectedDTO.getHeight(), actualDTO.getHeight());
                    assertEquals(expectedDTO.getWeight(), actualDTO.getWeight());
                    assertEquals(expectedDTO.getValue(), actualDTO.getValue());
                });
    }

    @Test
    @DisplayName("200 - GET /api/v1/body-mass-index")
    public void bodyMassIndex_findAll_successfully() {
        BodyMassIndexDTO expectedSecondDTO = BodyMassIndexDTO.builder()
                .id(101L)
                .height(173.00)
                .weight(56.00)
                .value(18.20)
                .build();

        BodyMassIndexDTO expectedThirdDTO = BodyMassIndexDTO.builder()
                .id(102L)
                .height(210.00)
                .weight(123.00)
                .value(30.20)
                .build();

        BodyMassIndexDTO expectedFourthDTO = BodyMassIndexDTO.builder()
                .id(1L)
                .height(192.00)
                .weight(107.25)
                .value(29.09)
                .build();

        List<BodyMassIndexDTO> expectedDtos = List.of(expectedFourthDTO, expectedThirdDTO, expectedSecondDTO);

        webTestClient.get()
                .uri("/api/v1/body-mass-index")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(BodyMassIndexDTO.class)
                .consumeWith(response -> {
                    List<BodyMassIndexDTO> actualDtos = response.getResponseBody();
                    assertNotNull(actualDtos);
                    assertEquals(3, actualDtos.size());
                    //assertEquals(expectedDtos, actualDtos);
                });
    }

    @Test
    @DisplayName("204 - PUT /api/v1/body-mass-index/102")
    public void bodyMassIndex_update_successfully() {
        BodyMassIndexDTO dto = BodyMassIndexDTO.builder()
                .id(102L)
                .height(210.00)
                .weight(110.00)
                .value(28.2)
                .build();

        webTestClient.put()
                .uri("/api/v1/body-mass-index/{id}", 102)
                .body(just(dto), BodyMassIndexDTO.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    @DisplayName("204 - DELETE /api/v1/body-mass-index/100")
    public void bodyMassIndex_delete_successfully() {
        webTestClient.delete()
                .uri("/api/v1/body-mass-index/{id}", 100)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }
}

package com.luv2code.health.tracker.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;
import static io.swagger.v3.oas.models.security.SecurityScheme.Type.OAUTH2;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                /*.components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme().type(HTTP).scheme("bearer").name("bearer-authentication").bearerFormat("JWT").in(HEADER)))*/
                .info(info())
                .servers(List.of(
                        new Server().url("http://localhost:8080")
                ));
    }

    private Info info() {
        return new Info()
                .title("Health Tracker API")
                .description("Health Tracker is a RESTfull service that allows users to tracker own health parameters.")
                .termsOfService("terms")
                .contact(contact())
                .license(licence())
                .version("1.0");
    }

    private Contact contact() {
        return new Contact()
                .email("luka.zugaj7@gmail.com")
                .name("Luka Zugaj");
    }

    private License licence() {
        return new License()
                .name("GNU")
                .url("https://github.com/thombergs/code-examples/blob/master/LICENSE");
    }
}

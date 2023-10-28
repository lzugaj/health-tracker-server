package com.luv2code.health.tracker.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "com.luv2code.health.tracker.token")
public class TokenProperties {

    private String secret;

}

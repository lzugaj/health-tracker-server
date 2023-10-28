package com.luv2code.health.tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

import static java.time.Clock.systemDefaultZone;

@Configuration
public class ClockConfig {

    @Bean
    public Clock clock() {
        return systemDefaultZone();
    }
}

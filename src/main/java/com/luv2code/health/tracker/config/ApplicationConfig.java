package com.luv2code.health.tracker.config;

import com.luv2code.health.tracker.auditing.ApplicationAuditAware;
import com.luv2code.health.tracker.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final AuthenticationService authenticationService;

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new ApplicationAuditAware(this.authenticationService);
    }
}

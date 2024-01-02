package com.luv2code.health.tracker;

import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAspectJAutoProxy
@EnableJpaRepositories(
        value = "com.luv2code.health.tracker.repository",
        repositoryBaseClass = BaseJpaRepositoryImpl.class
)
public class HealthTrackerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthTrackerServerApplication.class, args);
    }

}

package com.luv2code.health.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class HealthTrackerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthTrackerServerApplication.class, args);
    }

}

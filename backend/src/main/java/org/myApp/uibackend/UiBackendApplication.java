package org.myApp.uibackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

/**
 * Entry point of the backend system.
 *
 * Big Picture:
 * This Spring Boot service is the async backend that drives the UI.
 * The frontend polls this backend, and Selenium tests validate eventual consistency.
 */
@SpringBootApplication
public class UiBackendApplication {

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(UiBackendApplication.class);

        application.setDefaultProperties(
                Map.of("server.port", 8084)
        );

        application.run(args);
    }
}

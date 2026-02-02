package org.myApp.uibackend.config;

import jakarta.annotation.PostConstruct;

/**
 * - Triggers security validation during application startup.
 *
 * - Ensures backend fails fast if secrets are missing.
 */
public class SecurityConfig {

    private final SecurityProperties properties;

    public SecurityConfig(SecurityProperties properties) {
        this.properties = properties;
    }

    // This runs after Spring injects properties;
    @PostConstruct
    public void validateSecrets(){
        properties.validate();
    }
}

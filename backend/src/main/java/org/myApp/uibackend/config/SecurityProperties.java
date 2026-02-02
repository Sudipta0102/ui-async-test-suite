package org.myApp.uibackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Centralized security configuration loaded ONLY from environment variables.
 *
 * - Backend must never hardcode credentials
 * - Secrets must be injected at runtime (local, CI, Docker)
 * - Backend must fail fast if secrets are missing
 */
@Configuration // spring config class
@ConfigurationProperties(prefix = "security") // Maps SECURITY_* env vars
public class SecurityProperties {

    // simulates a real system credential without implementing full auth infra.
    private String username;

    // This value comes ONLY from env vars. Never logged or returned.
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // If secrets are missing, backend should NOT start.
    // Silent defaults cause insecure CI failures.
    public void validate(){

        if(username == null || username.isBlank()){
            throw new IllegalStateException("SECURITY_USERNAME is not set");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalStateException("SECURITY_PASSWORD is not set");
        }
    }

}

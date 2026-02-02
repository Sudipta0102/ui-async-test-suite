package org.myApp.uibackend.service;

import org.myApp.uibackend.config.SecurityProperties;
import org.springframework.stereotype.Service;

/**
 * Simple authentication service.
 *
 * - This is NOT a full auth system
 * - It exists to demonstrate secure secret handling
 * - Selenium tests must authenticate using env-only secrets
 */
@Service
public class AuthService {

    private final SecurityProperties properties;

    public AuthService(SecurityProperties properties) {
        this.properties = properties;
    }


     // Validate login credentials.
     // UI sends credentials. Backend compares against env-injected secrets
    public boolean authenticate(String username, String password){

        return properties.getUsername().equals(username)
                && properties.getPassword().equals(password);
    }
}

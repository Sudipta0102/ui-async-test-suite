package org.myApp.uibackend.api.auth;

import org.myApp.uibackend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Login API endpoint.
 *
 * - Static UI submits login form
 * - Selenium tests authenticate before dashboard access
 * - No sessions, tokens, or cookies needed
 */
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }


     // Login endpoint.
     // - Returns 200 if credentials match
     // - Returns 401 otherwise
     // - UI handles success/failure state
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginRequest request){

        // Validate creds
        boolean isAuthenticated = authService.authenticate(request.getUsername(), request.getPassword());

        // Reject invalid login attempts
        if(!isAuthenticated) throw new UnauthorizedException();

     }
}

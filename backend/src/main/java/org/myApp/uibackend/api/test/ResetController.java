package org.myApp.uibackend.api.test;

import org.myApp.uibackend.state.FeatureStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * - Test-only reset endpoint.
 *
 * - Selenium suites need deterministic resets to avoid cross-test contamination.
 */
@RestController
@RequestMapping("/api/test")
public class ResetController {

    private final FeatureStore store;

    public ResetController(FeatureStore store) {
        this.store = store;
    }

    // Reset backend to baseline state.
    // Used before each test suite run.
    @PostMapping("/reset")
    public void reset(){
        store.reset();
    }
}

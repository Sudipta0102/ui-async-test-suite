package org.myApp.uibackend.chaos;

import org.myApp.uibackend.config.ChaosProperties;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * - Injects random failures.
 *
 * - This simulates unstable async infra (timeouts, dropped events).
 * - UI test suite must classify failures correctly.
 */
@Component
public class FailureInjector {

    private final ChaosProperties chaos;
    private final Random random = new Random();

    public FailureInjector(ChaosProperties chaos) {
        this.chaos = chaos;
    }

    // fail randomly.
    // Helps demonstrate flakiness policy + retry boundaries.
    public void probableFail(){

        // no failures if disabled
        if(chaos.getFailureRate() <= 0) return;

        if(random.nextDouble() < chaos.getFailureRate()){
            throw new RuntimeException("Injected chaos failure");
        }
    }



}

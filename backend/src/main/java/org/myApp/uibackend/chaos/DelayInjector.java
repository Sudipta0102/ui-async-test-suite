package org.myApp.uibackend.chaos;

import org.myApp.uibackend.config.ChaosProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Random;

/**
 * - Injects random propagation delays.
 * - This simulates eventual consistency timing issues.
 * - Selenium tests must use proper waits, not sleeps.
 */
@Component
public class DelayInjector {

    private final ChaosProperties chaos;
    private final Random random = new Random();

    public DelayInjector(ChaosProperties chaos) {
        this.chaos = chaos;
    }

    // delay execution up to maxDelay
    // Represents backend async lag
    public void probableDelay(){

        long maxDelay = chaos.getMaxDelayMs();
        // no delay if disabled
        if(maxDelay <= 0) return;

        // delay within range
        long millis = random.nextLong(maxDelay);

        try{
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // we do not need to care about this.
        }

    }
}

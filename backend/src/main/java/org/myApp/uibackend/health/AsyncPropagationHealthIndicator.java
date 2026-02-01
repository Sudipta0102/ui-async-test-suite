package org.myApp.uibackend.health;

import org.myApp.uibackend.async.ExecutorRegistry;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * Health check for async propagation engine.
 *
 * - UI correctness depends on async executor health
 * - If executor is dead/saturated, UI tests will flake
 * - CI must fail fast instead of producing noisy failures
 */
@Component
public class AsyncPropagationHealthIndicator implements HealthIndicator {

    private final ExecutorService executor;

    public AsyncPropagationHealthIndicator(ExecutorRegistry registry) {
        this.executor = registry.asyncExecutor();
    }

    /**
     * - Reports health of async execution layer.
     * - Selenium should never run against a broken async system.
     */
    @Override
    public Health health(){

        // executor shutdown -> async propagation impossible
        if(executor.isShutdown()) {
            return Health
                    .down()
                    .withDetail("asyncExecutor","shutdown")
                    .build();
        }

        // executor terminated -> system unstable
        if(executor.isTerminated()){
            return Health
                    .down()
                    .withDetail("asyncExecutor","terminated")
                    .build();
        }

        // healthy async system
        return Health
                .up()
                .withDetail("asyncExwcutor", "running")
                .build();
    }
}

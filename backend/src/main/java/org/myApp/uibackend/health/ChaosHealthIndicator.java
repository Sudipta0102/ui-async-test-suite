package org.myApp.uibackend.health;

import org.myApp.uibackend.config.ChaosProperties;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Exposes active chaos configuration via health endpoint. (a bit over engineering i suppose)
 */
@Component
public class ChaosHealthIndicator implements HealthIndicator {

    private final ChaosProperties chaos;

    public ChaosHealthIndicator(ChaosProperties chaos) {
        this.chaos = chaos;
    }

    @Override
    public Health health() {

        return Health
                .up()
                .withDetail("failureRate", chaos.getFailureRate())
                .withDetail("maxDelayMs", chaos.getMaxDelayMs())
                .build();
    }
}

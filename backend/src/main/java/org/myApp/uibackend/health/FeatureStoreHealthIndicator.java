package org.myApp.uibackend.health;

import org.myApp.uibackend.state.FeatureStore;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Health check for in-memory state store.
 * If state is empty or corrupted, UI tests become meaningless.
 */
@Component
public class FeatureStoreHealthIndicator implements HealthIndicator {

    private final FeatureStore store;

    public FeatureStoreHealthIndicator(FeatureStore store) {
        this.store = store;
    }

    @Override
    public Health health(){

        // Snapshot used to validate baseline availability
        int size = store.snapshot().size();

        if (size == 0) {
            return Health
                    .down()
                    .withDetail("featureStore", "empty")
                    .build();
        }

        return Health
                .up()
                .withDetail("featureCount", size)
                .build();
    }
}

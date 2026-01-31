package org.myApp.uibackend.domain.feature;

import java.time.Instant;

public record FeatureState(
        FeatureFlag flag,
        boolean enabled,
        Instant lastUpdated
) {}

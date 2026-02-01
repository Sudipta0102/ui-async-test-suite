package org.myApp.uibackend.async;

import org.myApp.uibackend.domain.feature.FeatureFlag;

/**
 *
 * UI requests a toggle, backend schedules this async task.
 */
public record PropagationTask (
    // which feature is changing
    FeatureFlag flag,

    // desired new state
    boolean newState
){}

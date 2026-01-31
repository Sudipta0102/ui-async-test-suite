package org.myApp.uibackend.state;

import org.myApp.uibackend.domain.feature.FeatureFlag;
import org.myApp.uibackend.domain.feature.FeatureState;

import java.util.Map;

/**
 * Contract for storing feature state.
 *
 *
 * This is the backend "truth source" that the UI polls.
 * In real systems this might be Redis, DB, Kafka, etc.
 * Here it's in-memory for deterministic testability.
 */
public interface FeatureStore {

    // fetching current state of the flag
    // used by polling endpoints
    FeatureState get(FeatureFlag flag);

    // persisting a new state
    // Called asynchronously after propagation delay
    void put(FeatureState state);

    // holds all feature states
    // used by dashboard
    Map<FeatureFlag, FeatureState> snapshot();

    // Reset store to base-line
    // used by selenium tests to ensure clean isolation.
    void reset();

}

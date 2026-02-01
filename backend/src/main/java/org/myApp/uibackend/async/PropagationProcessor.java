package org.myApp.uibackend.async;

import org.myApp.uibackend.chaos.DelayInjector;
import org.myApp.uibackend.chaos.FailureInjector;
import org.myApp.uibackend.domain.feature.FeatureState;
import org.myApp.uibackend.state.FeatureStore;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

/**
 * - Executes propagation tasks asynchronously.
 *
 * - This is the "eventual consistency engine".
 * - UI toggles do NOT apply immediately.
 */
@Component
public class PropagationProcessor {

    // store where final status persisted
    private final FeatureStore store;

    // Chaos delay injector
    private final DelayInjector delay;

    // chaos failure injector
    private final FailureInjector failure;

    public PropagationProcessor(FeatureStore store, DelayInjector delay, FailureInjector failure){

        this.store = store;
        this.delay = delay;
        this.failure = failure;

    }


     // Apply async propagation with delay + chaos.
     // This is what makes UI tests non-trivial.
     public void propagate(PropagationTask task){

        // simulate eventual consistency lag with env var
         delay.probableDelay();

        // simulate random failure with env var
        failure.probableFail();

        // new state after propagation
         store.put(new FeatureState(task.flag(),
                                    task.newState(),
                                    Instant.now()));

     }

}

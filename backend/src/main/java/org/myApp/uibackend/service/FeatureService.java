package org.myApp.uibackend.service;

import jakarta.validation.constraints.NotNull;
import org.myApp.uibackend.async.ExecutorRegistry;
import org.myApp.uibackend.async.PropagationProcessor;
import org.myApp.uibackend.async.PropagationTask;
import org.myApp.uibackend.domain.feature.FeatureFlag;
import org.myApp.uibackend.domain.feature.FeatureState;
import org.myApp.uibackend.state.FeatureStore;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * - Service layer orchestrating async feature changes.
 *
 * - Controllers stay thin.
 * - Async complexity stays here.
 */
@Service
public class FeatureService {

    private final FeatureStore store;
    private final ExecutorRegistry executors;
    private final PropagationProcessor processor;

    public FeatureService(
            FeatureStore store,
            ExecutorRegistry executors,
            PropagationProcessor processor
    ) {
        this.store = store;
        this.executors = executors;
        this.processor = processor;

        // Initialize deterministic baseline state
        store.reset();
    }

    // Return current snapshot for UI polling.
    public Map<FeatureFlag, FeatureState> snapshot() {
        return store.snapshot();
    }


    // Request async state change.
    // UI triggers toggle, but backend applies it later.
    public void requestChange(FeatureFlag flag, boolean enabled){

        // submit propagation task asynchronously
        executors.asyncExecutor().submit(() ->
                processor.propagate(new PropagationTask(flag, enabled)));

    }

}

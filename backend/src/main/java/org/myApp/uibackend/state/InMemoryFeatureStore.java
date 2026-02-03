package org.myApp.uibackend.state;

import org.myApp.uibackend.domain.feature.FeatureFlag;
import org.myApp.uibackend.domain.feature.FeatureState;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * - Thread-safe in-memory implementation of FeatureStore.
 *
 * - This simulates a distributed system's state store
 * without requiring DB/Kafka, keeping tests deterministic.
 */
@Component
public class InMemoryFeatureStore implements FeatureStore {

    // Concurrent maps enables async writes safely
    private final Map<FeatureFlag, FeatureState> store = new ConcurrentHashMap<>();

    // UI polls this continuously, expecting continuous updates
    // So, in a way, it always returns the current state
    @Override
    public FeatureState get(FeatureFlag flag){

        return store.get(flag);
    }

    // Async propagation writes here after delay/chaos.
    @Override
    public void put(FeatureState state){
        store.put(state.flag(), state);
    }

    // holds all the flags
    // dashboard endpoint use this.
    @Override
    public Map<FeatureFlag, FeatureState> snapshot() {
        return Map.copyOf(store);
    }

    @Override
    public void reset() {
        store.clear();

        // initializing every flag is disabled
        for(FeatureFlag flag: FeatureFlag.values()){
            put(new FeatureState(flag, false, Instant.now()));
        }
    }
}

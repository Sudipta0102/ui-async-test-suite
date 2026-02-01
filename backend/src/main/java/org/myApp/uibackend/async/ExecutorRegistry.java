package org.myApp.uibackend.async;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * - Central async executor registry.
 *
 * - Async propagation must happen off-thread,
 * - so UI sees delayed eventual consistency updates.
 */
@Component
public class ExecutorRegistry {

    // fixed pool simulates background workers
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    // returns async executor for propagation tasks
    public ExecutorService asyncExecutor(){
        return executorService;
    }
}



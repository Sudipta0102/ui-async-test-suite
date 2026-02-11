package org.myApp.ui.config;

import org.myApp.ui.driver.ExecutionMode;

/**
 * ExecutionConfig is the single entry point for execution-related configuration.
 *
 * Responsibilities:
 * - Decide where the browser runs (LOCAL vs REMOTE)
 *
 * It does NOT:
 * - Know how drivers are created
 * - Know about Selenium Grid internals
 * - Resolve configuration precedence
 */
public final class ExecutionConfig {

    private static final String DEFAULT_EXECUTION_MODE = "LOCAL";

    private ExecutionConfig() {}

    /**
     * Resolves the execution mode for the current run.
     *
     * Resolution:
     *  - JVM:  -DexecutionMode=remote
     *  - ENV:  EXECUTION_MODE=remote
     *  - Default: LOCAL
     */
    public static ExecutionMode executionMode() {

        String mode = ConfigSelector.selectConfig(
                "executionMode",
                "EXECUTION_MODE",
                DEFAULT_EXECUTION_MODE
        );

        try {
            return ExecutionMode.valueOf(mode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unsupported execution mode: " + mode +
                            ". Supported values: " + java.util.Arrays.toString(ExecutionMode.values())
            );
        }
    }
}

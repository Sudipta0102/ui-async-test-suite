package org.myApp.ui.config;

/**
 * EnvironmentConfig centralizes environment-level configuration.
 *
 * Responsibilities:
 * - Resolve the base application URL
 *
 * It does NOT:
 * - Navigate the browser
 * - Know about tests or TestNG
 * - Contain execution or driver logic
 */
public final class EnvironmentConfig {

    private static final String DEFAULT_BASE_URL = "http://localhost:8084";

    private EnvironmentConfig() {
        // utility class
    }

    /**
     * Resolves the base URL for the application under test.
     *
     * Resolution order:
     *  - JVM system property: -DbaseUrl=http://...
     *  - Environment variable: BASE_URL=http://...
     *  - Default: http://localhost:8084
     */
    public static String baseUrl() {

        return ConfigSelector.selectConfig(
                "baseUrl",
                "BASE_URL",
                DEFAULT_BASE_URL
        );
    }
}

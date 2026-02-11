package org.myApp.ui.config;

import java.util.Arrays;

/**
 * ConfigResolver is the single source of truth for resolving configuration values.
 *
 * Precedence (highest → lowest):
 * 1. JVM system properties   (-Dkey=value)
 * 2. Environment variables   (KEY=value)
 * 3. Default value
 *
 * This prevents:
 * - duplicated resolution logic
 * - inconsistent precedence rules
 * - "works on my machine" bugs
 */
public class ConfigSelector {

    // should not create any instances
    private ConfigSelector(){}


    public static String selectConfig(
            String systemPropKey,
            String envVarKey,
            String defaultValue
    ){
        // Highest priority: -Dkey=value
        String fromSysProp = System.getProperty(systemPropKey);

        // second priority: ENV_VAR=value
        String fromEnvVar = System.getenv(envVarKey);

        // pick the first value;
        return pickConfig(fromSysProp, fromEnvVar, defaultValue);
    }

    /**
     * Variant for cases where system property and env var use the same key name.
     *
     * Example:
     *  -DbaseUrl=...
     *  BASEURL=...
     */
    public static String selectConfig(
            String key,
            String defaultValue
    ) {
        return selectConfig(key, key.toUpperCase(), defaultValue);
    }


    /**
     * Helper that returns the first non-null, non-empty value.
     * Order matters — earlier values win.
     */
    private static String pickConfig(String... values) {
        return Arrays.stream(values)
                .filter(value -> value != null && !value.trim().isEmpty())
                .findFirst()
                .orElse(null);
    }
}

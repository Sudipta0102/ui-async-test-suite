package org.myApp.ui.config;

/**
 * Centralized timeout configuration.
 *
 * All timeout values are expressed in seconds.
 *
 * This class defines:
 * - Defaults
 * - Override keys
 *
 * It does NOT:
 * - Apply timeouts to WebDriver
 * - Create waits
 * - Contain polling logic
 */
public final class TimeoutConfig {

    private static final String PAGE_LOAD_KEY = "pageLoadTimeout";
    private static final String IMPLICIT_WAIT_KEY = "implicitWait";
    private static final String EXPLICIT_WAIT_KEY = "explicitWait";

    private static final int DEFAULT_PAGE_LOAD_TIMEOUT = 30;
    private static final int DEFAULT_IMPLICIT_WAIT = 0;
    private static final int DEFAULT_EXPLICIT_WAIT = 10;

    private TimeoutConfig() {}

    /**
     * @return page load timeout in seconds
     *
     * Resolution:
     *  - JVM: -DpageLoadTimeout=30
     *  - ENV: PAGELOADTIMEOUT=30
     *  - Default: 30
     */
    public static int pageLoadTimeoutSeconds() {
        return parseInt(
                ConfigSelector.selectConfig(
                        PAGE_LOAD_KEY,
                        DEFAULT_PAGE_LOAD_TIMEOUT + ""
                ),
                DEFAULT_PAGE_LOAD_TIMEOUT,
                PAGE_LOAD_KEY
        );
    }

    /**
     * @return implicit wait timeout in seconds
     *
     * Default is intentionally 0 to avoid hidden waits.
     */
    public static int implicitWaitSeconds() {
        return parseInt(
                ConfigSelector.selectConfig(
                        IMPLICIT_WAIT_KEY,
                        DEFAULT_IMPLICIT_WAIT + ""
                ),
                DEFAULT_IMPLICIT_WAIT,
                IMPLICIT_WAIT_KEY
        );
    }

    /**
     * @return explicit wait timeout in seconds
     */
    public static int explicitWaitSeconds() {
        return parseInt(
                ConfigSelector.selectConfig(
                        EXPLICIT_WAIT_KEY,
                        DEFAULT_EXPLICIT_WAIT + ""
                ),
                DEFAULT_EXPLICIT_WAIT,
                EXPLICIT_WAIT_KEY
        );
    }

    private static int parseInt(String value, int defaultValue, String key) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid timeout value for '" + key + "': " + value +
                            ". Must be an integer representing seconds."
            );
        }
    }
}
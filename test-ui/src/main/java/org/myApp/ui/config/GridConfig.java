package org.myApp.ui.config;

/**
 * GridConfig centralizes all Selenium Grid related configuration.
 *
 * Responsibilities:
 * - Provide the Selenium Grid URL
 *
 * It does NOT:
 * - Decide whether Grid is used
 * - Create RemoteWebDrivers
 * - Know about browsers or execution mode
 */
public final class GridConfig {

    private static final String DEFAULT_GRID_URL = "http://localhost:4444/wd/hub";

    private GridConfig() {}

    /**
     * Resolves the Selenium Grid URL.
     *
     * Resolution:
     *  - JVM: -DseleniumGridUrl=http://grid:4444/wd/hub
     *  - ENV: SELENIUM_GRID_URL=http://grid:4444/wd/hub
     *  - Default: http://localhost:4444/wd/hub
     */
    public static String gridUrl() {
        return ConfigSelector.selectConfig(
                "seleniumGridUrl",
                "SELENIUM_GRID_URL",
                DEFAULT_GRID_URL
        );
    }
}
package org.myApp.ui.config;

import org.myApp.ui.driver.BrowserType;

/**
 * BrowserConfig is the single entry point for all browser-related configuration.
 *
 * Responsibilities:
 * - Decide which browser to use
 * - Provide a safe default
 *
 * It does NOT:
 * - Know how drivers are created
 * - Know about Selenium or TestNG
 * - Resolve configuration precedence
 */
public final class BrowserConfig {

    private static final String DEFAULT_BROWSER = "CHROME";

    private BrowserConfig() {}

    /**
     * Resolves the browser type for the current run.
     *
     * Resolution:
     *  - JVM:  -Dbrowser=chrome
     *  - ENV:  BROWSER=chrome
     *  - Default: CHROME
     */
    public static BrowserType browserType() {

        String browser = ConfigSelector.selectConfig(
                "browser",
                "BROWSER",
                DEFAULT_BROWSER
        );

        try {
            return BrowserType.valueOf(browser.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unsupported browser: " + browser +
                            ". Supported values: " + java.util.Arrays.toString(BrowserType.values())
            );
        }
    }
}

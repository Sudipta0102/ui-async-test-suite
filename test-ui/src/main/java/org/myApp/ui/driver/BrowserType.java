package org.myApp.ui.driver;

import java.util.Arrays;

/**
 * Supported browsers.
 * Adding a new browser should require:
 * 1. New enum value
 * 2. New Strategy class
 * Not 15 if-else blocks
 *
 * - browser should be conducted from env
 * - defaulting to chrome for no reason at all
 * - Resolved from:
 *   1. JVM system property (-Dbrowser)
 *   2. Environment variable (BROWSER)
 *   3. Default (CHROME)
 */
public enum BrowserType {

    CHROME,
    FIREFOX;

    public static BrowserType fromEnv(){

        // -Dbrowser=firefox i.e Maven
        String browserFromSystemProp = System.getProperty("browser");

        // env var BROWSER=firefox
        String browserFromEnv = System.getenv("BROWSER");

        String browser = pickFirstDefinedBrowser(browserFromSystemProp, browserFromEnv, "CHROME");

        return Arrays.stream(values())
                .filter(browserType -> browserType.name().equalsIgnoreCase(browser))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Unsupported Browser:" + browser));

    }

    private static String pickFirstDefinedBrowser(String... options){
        return Arrays.stream(options)
                .filter(op -> op != null && !op.trim().isEmpty())
                .findFirst()
                .orElse(null);
    }
}

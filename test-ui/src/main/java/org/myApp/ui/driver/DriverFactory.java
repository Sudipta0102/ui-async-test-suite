package org.myApp.ui.driver;


import org.myApp.ui.config.BrowserConfig;
import org.myApp.ui.config.ExecutionConfig;
import org.myApp.ui.driver.strategy.*;

import java.util.EnumMap;
import java.util.Map;

/**
 *
 * Answers:
 *  - Which browser?
 *  - Where is it running?
 *
 * Tried to do this without:
 *  - if-else, switch
 *
 *  - Manage lifecycle
 *  - Know about tests
 *  - Know about TestNG
 *
 * Tests never talk to this class directly.
 */
public final class DriverFactory {

    // What I am trying to enable here is this below line:
    // STRATEGY.get(executionMode)
    //         .get(browserType);
    //
    // (mode, browser) -> strategy
    //
    // this leads me to use this data structure here.

    // Why enum map. I really don't know. I guess it works faster than Hashmap.
    // Also, Both keys are enums here.
    // Most importantly, It is FIXED set of keys like any other enums.
    private static final Map<ExecutionMode, Map<BrowserType, BrowserStrategy>> STRATEGY
                                                                = new EnumMap<>(ExecutionMode.class);

    // static block runs once
    static {
        // Local strategies
        Map<BrowserType, BrowserStrategy> localStrategies = new EnumMap<>(BrowserType.class);

        localStrategies.put(BrowserType.CHROME, new LocalChromeStrategy());
        localStrategies.put(BrowserType.FIREFOX, new LocalFirefoxStrategy());

        STRATEGY.put(ExecutionMode.LOCAL, localStrategies);

        // Remote Strategies
        Map<BrowserType, BrowserStrategy> remoteStrategies = new EnumMap<>(BrowserType.class);

        remoteStrategies.put(BrowserType.CHROME, new RemoteChromeStrategy());
        remoteStrategies.put(BrowserType.FIREFOX, new RemoteFirefoxStrategy());

        STRATEGY.put(ExecutionMode.REMOTE, remoteStrategies);
    }

    // Nobody should instantiate this class
    private DriverFactory(){

    }

    // configuration becomes behavior here
    public static BrowserStrategy selectStrategy(){

        // where is browser running?
        ExecutionMode executionMode = ExecutionConfig.executionMode();

        // Which browser?
        BrowserType browserType = BrowserConfig.browserType();

        // picking strategies from chosen configs
        Map<BrowserType, BrowserStrategy> browserTypeBrowserStrategyMap = STRATEGY.get(executionMode);

        // defensive checking
        if(browserTypeBrowserStrategyMap == null){
            throw new IllegalStateException("No driver strategies configured for execution mode: " + executionMode);
        }

        // chrome/firefox
        BrowserStrategy browserStrategy = browserTypeBrowserStrategyMap.get(browserType);

        if (browserStrategy == null) {
            throw new IllegalStateException("No WebDriver strategy for combination: " + executionMode + " + " + browserType);
        }

        return browserStrategy;
    }

}

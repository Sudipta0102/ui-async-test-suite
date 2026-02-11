package org.myApp.ui.driver;

import org.openqa.selenium.WebDriver;

/**
 * - responsible for webdriver lifecycle
 * - one browser one thread
 * - Tests talk to this class
 */
public class DriverProvider {

    // threadlocal meaning each test gets its own thread
    private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    // No instantiation allowed. It's more of a utility class. Why would you need to instantiate?
    private DriverProvider(){}

    // Get the webdriver for the current test thread
    // uses driverfactory to use create strategy
    // lazily create when needed
    public static WebDriver get(){

        // if no browser yet then create
        if(DRIVER_THREAD_LOCAL.get()==null){

            WebDriver driver = DriverFactory.selectStrategy().createDriver();

            // storing it for this thread only
            DRIVER_THREAD_LOCAL.set(driver);
        }
        return DRIVER_THREAD_LOCAL.get();
    }

    // Quit and clean for the current thread
    public static void quit(){

        WebDriver driver = DRIVER_THREAD_LOCAL.get();

        if(driver != null){

            // quit
            driver.quit();
            //clean to prevent leaks
            DRIVER_THREAD_LOCAL.remove();

        }
    }
}

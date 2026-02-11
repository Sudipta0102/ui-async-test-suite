package org.myApp.ui.base;

import org.myApp.ui.config.EnvironmentConfig;
import org.myApp.ui.config.TimeoutConfig;
import org.myApp.ui.driver.DriverProvider;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp(){

        // Get (or lazily create) the WebDriver for this test thread
        driver = DriverProvider.get();

        // Apply global timeouts once per test
        applyTimeouts(driver);

        // Optional but recommended:
        // Start every test from the application root
        driver.get(EnvironmentConfig.baseUrl());

        // NOTE:
        // Backend reset (e.g. /api/test/reset) can be added here later.
        // Keeping it out for now keeps BaseTest focused and readable.

    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result){
        // If the test failed, this is the hook point for:
        // - screenshots
        // - DOM dumps if applicable
        // - backend health snapshots if needed
        //
        // I am keeping this empty for now.
        // Diagnostics will be added in a dedicated layer.

        // Always quit the browser to avoid leaks
        DriverProvider.quit();
    }

    private void applyTimeouts(WebDriver driver) {

        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(TimeoutConfig.pageLoadTimeoutSeconds())
        );

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(TimeoutConfig.implicitWaitSeconds())
        );

    }

}

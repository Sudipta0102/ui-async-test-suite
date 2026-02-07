package org.myApp.ui.driver.strategy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class RemoteFirefoxStrategy implements BrowserStrategy{

    @Override
    public WebDriver createDriver() {

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");

        return createRemoteDriver(options);
    }

    private WebDriver createRemoteDriver(FirefoxOptions options) {

        try {
            return new RemoteWebDriver(new URL(gridUrl()), options);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to start Remote Firefox session", e
            );
        }

    }

    private String gridUrl() {

        return System.getProperty("seleniumGridUrl",
                                System.getenv().getOrDefault("SELENIUM_GRID_URL",
                                        "http://localhost:4444/wd/hub"));
    }
}

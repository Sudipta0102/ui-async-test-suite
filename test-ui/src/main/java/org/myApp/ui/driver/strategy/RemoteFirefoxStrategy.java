package org.myApp.ui.driver.strategy;

import org.myApp.ui.config.GridConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class RemoteFirefoxStrategy implements BrowserStrategy{

    @Override
    public WebDriver createDriver() {
        try {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--width=1920", "--height=1080");

            return new RemoteWebDriver(
                    new URL(GridConfig.gridUrl()),
                    options
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to start remote firefox session", e);
        }
    }
}

package org.myApp.ui.driver.strategy;

import org.myApp.ui.config.GridConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

/**
 * Chrome running on Selenium Grid.
 *
 */
public class RemoteChromeStrategy implements BrowserStrategy{

    @Override
    public WebDriver createDriver() {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-dev-shm-usage", "--no-sandbox");

            return new RemoteWebDriver(
                    new URL(GridConfig.gridUrl()),
                    options
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to start remote chrome session", e);
        }
    }
}

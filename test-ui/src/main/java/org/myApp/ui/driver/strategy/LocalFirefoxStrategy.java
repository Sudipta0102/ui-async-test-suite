package org.myApp.ui.driver.strategy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class LocalFirefoxStrategy implements BrowserStrategy{

    @Override
    public WebDriver createDriver() {

        FirefoxOptions options = new FirefoxOptions();

        // Keep viewport consistent with Chrome
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");

        return new FirefoxDriver(options);
    }
}

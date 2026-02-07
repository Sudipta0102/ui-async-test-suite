package org.myApp.ui.driver.strategy;

import org.openqa.selenium.WebDriver;

/**
 * Create and return a webdriver
 */
@FunctionalInterface
public interface BrowserStrategy {

    WebDriver createDriver();

}

package org.myApp.ui.driver.strategy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LocalChromeStrategy implements BrowserStrategy{

    @Override
    public WebDriver createDriver(){

        ChromeOptions options = new ChromeOptions();

        // forces chrome to use /tmp folder instead of /dev/shm which sometimes defaults to 64mb
        // which causes websites to crash in chrome when testing.
        // Literally, it's not applicable here, but I put it for scalability sake.
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        options.addArguments("--start-maximized");

        return new ChromeDriver(options);
    }
}

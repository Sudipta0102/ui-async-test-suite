package org.myApp.ui.driver.strategy;

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

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        //WebDriver remoteDriver = createRemoteDriver(options);
        //return remoteDriver;

        return createRemoteDriver(options);
    }

    private WebDriver createRemoteDriver(ChromeOptions options){

        try{
            return new RemoteWebDriver(new URL(gridUrl()), options);
        }catch (Exception e){
            throw new RuntimeException("Failed to start remote chrome session");
        }
    }

    private String gridUrl(){
        return System.getProperty("seleniumGridUrl",
                                        System.getenv().getOrDefault("SELENIUM_GRID_URL",
                                                                "http://localhost:4444/wd/hub"));
    }
}

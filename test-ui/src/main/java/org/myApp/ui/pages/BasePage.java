package org.myApp.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * BasePage is the root of all Page Objects.
 *
 * Responsibilities:
 * - Hold WebDriver reference
 * - Initialize PageFactory elements
 * - Provide low-level UI interactions
 *
 * It does NOT:
 * - Contain waits
 * - Perform assertions
 * - Contain navigation logic
 */
public class BasePage {

    protected final WebDriver driver;

    protected BasePage(WebDriver driver){

        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    protected void click(WebElement element) {
        element.click();
    }

    protected void type(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    protected String textOf(WebElement element) {
        return element.getText();
    }

    protected boolean isDisplayed(WebElement element) {
        return element.isDisplayed();
    }


}

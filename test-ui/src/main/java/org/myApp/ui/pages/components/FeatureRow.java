package org.myApp.ui.pages.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class FeatureRow {

    private final WebDriver driver;

    // -------- Row-scoped elements --------

    @FindBy(css = "td:nth-child(1)")
    private WebElement featureName;

    @FindBy(css = "td:nth-child(2)")
    private WebElement state;

    @FindBy(css = "td:nth-child(3)")
    private WebElement status;

    @FindBy(css = "td:nth-child(4) button")
    private WebElement toggleButton;

    public FeatureRow(WebDriver driver, WebElement rowElement) {
        this.driver = driver;

        // Initializing PageFactory using the row as search context
        PageFactory.initElements(new DefaultElementLocatorFactory(rowElement), this);
    }

    public String name() {
        return featureName.getText();
    }

    public String state() {
        return state.getText();
    }

    public String status() {
        return status.getText();
    }

    public void toggle() {
        toggleButton.click();
    }
}

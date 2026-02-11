package org.myApp.ui.pages;

import org.myApp.ui.pages.components.FeatureRow;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Optional;

public class DashboardPage extends BasePage{

    @FindBy(id = "backend-health-status")
    private WebElement backendHealthStatus;

    @FindBy(id = "chaos-enabled")
    private WebElement chaosEnabledStatus;

    @FindBy(id = "last-refresh-timestamp")
    private WebElement lastRefreshTimestamp;

    @FindBy(css = "#features-table-body tr")
    private List<WebElement> featureRows;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public String backendStatus() {
        return backendHealthStatus.getText();
    }

    public String chaosEnabledStatus() {
        return chaosEnabledStatus.getText();
    }

    public String lastRefreshTime() {
        return lastRefreshTimestamp.getText();
    }

    public FeatureRow feature(String featureName) {
        return findFeatureRow(featureName)
                .orElseThrow(() -> new IllegalArgumentException("Feature not found in dashboard: " + featureName));
    }

    public List<FeatureRow> allFeatures() {
        return featureRows.stream()
                .map(row -> new FeatureRow(driver, row))
                .toList();
    }

    private Optional<FeatureRow> findFeatureRow(String featureName) {
        return featureRows.stream()
                .map(row -> new FeatureRow(driver, row))
                .filter(feature -> feature.name().equals(featureName))
                .findFirst();
    }

}

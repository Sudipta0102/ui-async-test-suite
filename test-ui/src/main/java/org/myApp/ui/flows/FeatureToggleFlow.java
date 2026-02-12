package org.myApp.ui.flows;

import org.myApp.ui.pages.DashboardPage;
import org.myApp.ui.pages.components.FeatureRow;

public class FeatureToggleFlow {

    private final DashboardPage dashboard;

    public FeatureToggleFlow(DashboardPage dashboard) {
        this.dashboard = dashboard;
    }


    // Toggles a feature flag by name.
    //
    // This method performs the action only.
    // It does NOT wait for state convergence yet.
    public void toggleFeature(String featureName) {

        FeatureRow feature = dashboard.feature(featureName);

        feature.toggle();
    }


    // Reads the current state of a feature.
    public String featureState(String featureName) {

        return dashboard
                .feature(featureName)
                .state();
    }


    // Reads the current status of a feature. (ACTIVE / PENDING / FAILED)
    public String featureStatus(String featureName) {

        return dashboard
                .feature(featureName)
                .status();
    }

}

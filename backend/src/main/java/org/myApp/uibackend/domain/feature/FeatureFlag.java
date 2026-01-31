package org.myApp.uibackend.domain.feature;

/**
 * - Represents feature flags exposed in the UI dashboard.
 *
 * - Selenium tests toggle these flags and observe async propagation behavior.
 */
public enum FeatureFlag {

    DARK_MODE,
    BETA_SEARCH,
    FAST_CHECKOUT

}

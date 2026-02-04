package org.myApp.uibackend.domain.feature;

/**
 * - Represents feature flags exposed in the UI dashboard.
 *
 * - Selenium tests toggle these flags and observe async propagation behavior.
 */
public enum FeatureFlag {

    SLOW_COOKED_MUTTON,
    LATE_DELIVERY,
    MEET_AT_CROSSROADS

}

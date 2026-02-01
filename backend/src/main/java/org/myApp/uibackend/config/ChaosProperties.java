package org.myApp.uibackend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Controls async(chaos) instability via env vars instead of code changes.
 */
@Configuration
@ConfigurationProperties(prefix = "chaos")
public class ChaosProperties {

    // Probability (0.0 – 1.0) of injected failure
    private double failureRate;

    private long maxDelayMs;

    public long getMaxDelayMs() {
        return maxDelayMs;
    }

    public void setMaxDelayMs(long maxDelayMs) {
        this.maxDelayMs = maxDelayMs;
    }

    public double getFailureRate() {
        return failureRate;
    }

    public void setFailureRate(double failureRate) {
        this.failureRate = failureRate;
    }
}

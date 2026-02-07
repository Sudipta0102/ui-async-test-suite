package org.myApp.ui.driver;

import java.util.Arrays;

public enum ExecutionMode {

    LOCAL,
    REMOTE;

    public static ExecutionMode fromEnv(){

        // For Maven
        String execModeFromSystemProp = System.getProperty("executionMode");

        // For env var (we can use it in CI)
        String execModeFromEnvVar = System.getenv("EXECUTION_MODE");

        // Fallback
        String execMode = pickFirstDefinedExecMode(execModeFromSystemProp, execModeFromEnvVar, "LOCAL");

        
        return Arrays.stream(values())
                .filter(executionMode -> executionMode.name().equalsIgnoreCase(execMode))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Unsupported execution mode:" + execMode + ". LOCAL or REMOTE"));
    }

    private static String pickFirstDefinedExecMode(String... options){

        return Arrays.stream(options)
                .filter(op->op!=null && op.trim().isEmpty())
                .findFirst()
                .orElse(null);

    }
}

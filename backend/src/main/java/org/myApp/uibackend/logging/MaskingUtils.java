package org.myApp.uibackend.logging;

import java.util.regex.Pattern;

/**
 * Utility for masking sensitive values.
 * Centralized masking logic prevents accidental leaks.
 */
public class MaskingUtils {

    // Regex for common secret keys
    private static final Pattern SECRET_PATTERN =
            Pattern.compile(
                    "(password|token|secret)=([^&]+)",
                    Pattern.CASE_INSENSITIVE
            );

    private MaskingUtils() {
        // Utility class
    }

    /**
     * Masks secret values in query strings.
     */
    public static String mask(String input) {

        if (input == null) return null;

        // Replace secret values with ******
        return SECRET_PATTERN.matcher(input)
                .replaceAll("$1=******");
    }

}

package com.github.vanessaopensource.vanessarunner.steps.core;

import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Messages {
    private static final String BUNDLE_NAME = "com.github.vanessaopensource.vanessarunner.steps.messages";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private Messages() {
        throw new IllegalStateException("Utility class");
    }

    @NonNull
    public static String getString(final String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}

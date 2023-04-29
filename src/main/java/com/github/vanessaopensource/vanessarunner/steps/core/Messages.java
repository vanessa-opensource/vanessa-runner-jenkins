package com.github.vanessaopensource.vanessarunner.steps.core;

import edu.umd.cs.findbugs.annotations.NonNull;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
    private static final String BUNDLE_NAME = "com.github.vanessaopensource.vanessarunner.steps.messages";

    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    @NonNull
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}

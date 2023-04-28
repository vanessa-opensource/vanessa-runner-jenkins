package com.github.vanessaopensource.vanessarunner.steps;

public enum SessionAction {
    LOCK("lock"),
    UNLOCK("unlock"),
    KILL("kill");

    private final String literal;

    SessionAction(String literal) {
        this.literal = literal;
    }

    @Override
    public String toString() {
        return literal;
    }
}

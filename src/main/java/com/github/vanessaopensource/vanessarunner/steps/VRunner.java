package com.github.vanessaopensource.vanessarunner.steps;

import lombok.Getter;
import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.Set;

abstract public class VRunner extends Step {

    public static final String ENV_DBUSER = "RUNNER_DBUSER";
    public static final String ENV_DBPWD = "RUNNER_DBPWD";
    public static final String ENV_STORAGE_USER = "RUNNER_STORAGE_USER";
    public static final String ENV_STORAGE_PWD = "RUNNER_STORAGE_PWD";

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String ibConnection;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String databaseCredentialsID;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String ucCode;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String v8Version;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private Boolean noCacheUse;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String additional;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private Boolean ordinaryApp;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String language;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String locale;

    public VRunner() {
    }

    public abstract static class Descriptor extends StepDescriptor {
        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return VRunnerContext.getRequiredContext();
        }
    }
}

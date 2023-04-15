package com.github.vanessaopensource.vanessarunner.steps;

import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.Set;

abstract public class VRunner extends Step {

    public static final String ENV_DBUSER = "RUNNER_DBUSER";
    public static final String ENV_DBPWD = "RUNNER_DBPWD";
    public static final String ENV_STORAGE_USER = "RUNNER_STORAGE_USER";
    public static final String ENV_STORAGE_PWD = "RUNNER_STORAGE_PWD";

    @DataBoundSetter
    String ibConnection;

    @DataBoundSetter
    String databaseCredentialsID;

    @DataBoundSetter
    String ucCode;

    @DataBoundSetter
    String v8Version;

    @DataBoundSetter
    Boolean noCacheUse;

    @DataBoundSetter
    String additional;

    @DataBoundSetter
    Boolean ordinaryApp;

    @DataBoundSetter
    String language;

    @DataBoundSetter
    String locale;

    @DataBoundSetter
    String settings;

    public VRunner() {
    }

    public abstract static class Descriptor extends StepDescriptor {
        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return VRunnerContext.getRequiredContext();
        }
    }
}

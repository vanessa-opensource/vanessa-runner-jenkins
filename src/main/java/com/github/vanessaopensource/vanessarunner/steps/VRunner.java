package com.github.vanessaopensource.vanessarunner.steps;

import hudson.util.FormValidation;
import lombok.Getter;
import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import java.util.Set;

abstract public class VRunner extends Step {

    public static final String ENV_DBUSER = "RUNNER_DBUSER";
    public static final String ENV_DBPWD = "RUNNER_DBPWD";
    public static final String ENV_STORAGE_USER = "RUNNER_STORAGE_USER";
    public static final String ENV_STORAGE_PWD = "RUNNER_STORAGE_PWD";
    public static final String ENV_CLUSTER_USER = "RUNNER_CLUSTERADMIN_USER ";
    public static final String ENV_CLUSTER_PWD = "RUNNER_CLUSTERADMIN_PWD";

    @Getter
    @DataBoundSetter
    String ibConnection = "";

    @Getter
    @DataBoundSetter
    String databaseCredentialsID = "";

    @Getter
    @DataBoundSetter
    String ucCode = "";

    @Getter
    @DataBoundSetter
    String v8Version = "8.3";

    @Getter
    @DataBoundSetter
    Boolean noCacheUse = false;

    @Getter
    @DataBoundSetter
    String additional = "";

    @Getter
    @DataBoundSetter
    Boolean ordinaryApp = false;

    @Getter
    @DataBoundSetter
    String language = "";

    @Getter
    @DataBoundSetter
    String locale = "";

    @Getter
    @DataBoundSetter
    String settings = "";

    public VRunner() {
    }

    public abstract static class Descriptor extends StepDescriptor {
        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return VRunnerContext.getRequiredContext();
        }
    }
}

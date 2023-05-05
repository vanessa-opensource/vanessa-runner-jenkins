package com.github.vanessaopensource.vanessarunner.steps.core;

import hudson.AbortException;
import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.Set;

@Getter
@Setter
abstract public class VRunner extends Step {

    public static final String ENV_DBUSER = "RUNNER_DBUSER";
    public static final String ENV_DBPWD = "RUNNER_DBPWD";
    public static final String ENV_STORAGE_USER = "RUNNER_STORAGE_USER";
    public static final String ENV_STORAGE_PWD = "RUNNER_STORAGE_PWD";
    public static final String ENV_CLUSTER_USER = "RUNNER_CLUSTERADMIN_USER ";
    public static final String ENV_CLUSTER_PWD = "RUNNER_CLUSTERADMIN_PWD";

    @DataBoundSetter
    private String databaseCredentialsID = "";

    @DataBoundSetter
    private String v8Version = "8.3";

    @DataBoundSetter
    private String settings = "";

    public void setCommandContext(VRunnerContext context) throws AbortException {

        context.addParameter(v8Version, "--v8version");

        context.addParameter(settings, "--settings");

        context.addCredentialsEnv(databaseCredentialsID, VRunner.ENV_DBUSER, VRunner.ENV_DBPWD);
    }

    @Override
    public StepExecution start(StepContext context)  {
        return new VRunnerExecution(context, this);
    }

    public abstract static class Descriptor extends StepDescriptor {
        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return VRunnerContext.getRequiredContext();
        }
    }
}

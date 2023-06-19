package com.github.vanessaopensource.vanessarunner.vrunner;

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
public abstract class VRunner extends Step {

    public static final String ENV_DBUSER = "RUNNER_DBUSER";
    public static final String ENV_DBPWD = "RUNNER_DBPWD";
    public static final String ENV_STORAGE_USER = "RUNNER_STORAGE_USER";
    public static final String ENV_STORAGE_PWD = "RUNNER_STORAGE_PWD";
    public static final String ENV_CLUSTER_USER = "RUNNER_CLUSTERADMIN_USER ";
    public static final String ENV_CLUSTER_PWD = "RUNNER_CLUSTERADMIN_PWD";

    @DataBoundSetter
    private String settings = "";

    public void setCommandContext(final VRunnerContext context) throws AbortException {
        context.addParameter(settings, "--settings");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StepExecution start(final StepContext context)  {
        return new VRunnerExecution(context, this);
    }

    public abstract static class Descriptor extends StepDescriptor {

        /**
         * {@inheritDoc}
         */
        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return VRunnerContext.getRequiredContext();
        }
    }
}

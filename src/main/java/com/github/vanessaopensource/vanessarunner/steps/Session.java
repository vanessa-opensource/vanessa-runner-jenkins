package com.github.vanessaopensource.vanessarunner.steps;

import hudson.AbortException;
import lombok.Getter;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundSetter;

public abstract class Session extends VRunner {

    @Getter
    @DataBoundSetter
    String rasHost = "";

    @Getter
    @DataBoundSetter
    Integer rasPort = 1545;

    @Getter
    @DataBoundSetter
    String dbName = "";

    @Getter
    @DataBoundSetter
    String clusterCredentialsID = "";

    @Override
    public StepExecution start(StepContext context) throws AbortException {
        return new SessionExecution(context, this);
    }

    public void addCommandContext(VRunnerContext context) throws AbortException {
        context.addParameter(rasHostPort(), "--ras");
        context.addParameter(dbName, "--db");
        context.addCredentialsEnv(clusterCredentialsID, VRunner.ENV_CLUSTER_USER, VRunner.ENV_CLUSTER_PWD);
    }

    String rasHostPort() {
        return String.format("%s:%d", rasHost, rasPort);
    }

    public static class SessionExecution extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient Session step;

        protected SessionExecution(StepContext context, Session step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) throws AbortException {
            step.addCommandContext(context);
        }
    }
}

package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class SessionStep extends VRunner {

    @Getter
    @DataBoundSetter
    SessionAction action;

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

    @Getter
    @DataBoundSetter
    String lockMessage = "";

    @Getter
    @DataBoundSetter
    Integer lockStartAt = 0;

    @Getter
    @DataBoundSetter
    Boolean lockEndClear = false;

    @Getter
    @DataBoundSetter
    Boolean killWithNoLock = false;

    @DataBoundConstructor
    public SessionStep() {
    }

    @Override
    public StepExecution start(StepContext context) throws AbortException {
        switch (action) {
            case LOCK: return new SessionLockExecution(context, this);
            case UNLOCK: return new SessionUnlockExecution(context, this);
            case KILL: return new SessionKillExecution(context, this);
            default: throw new AbortException("Unsupportable action");
        }
    }

    private String rasHostPort() {
        return String.format("%s:%d", rasHost, rasPort);
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerSession";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("SessionStep.DisplayName");
        }
    }

    public static class SessionLockExecution extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient SessionStep step;

        protected SessionLockExecution(StepContext context, SessionStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) throws AbortException {
            context.setCommand("session");
            context.setCommand("lock");

            context.addParameter(step.rasHostPort(), "--ras");
            context.addParameter(step.dbName, "--db");
            context.addParameter(step.lockMessage, "--lockmessage");
            context.addParameter(step.lockStartAt, "--lockstartat");
            context.addSwitch(step.lockEndClear, "--lockendclear");

            context.addCredentialsEnv(step.clusterCredentialsID, VRunner.ENV_CLUSTER_USER, VRunner.ENV_CLUSTER_PWD);
        }

    }

    public static class SessionUnlockExecution extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient SessionStep step;

        protected SessionUnlockExecution(StepContext context, SessionStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) throws AbortException {
            context.setCommand("session");
            context.setCommand("unlock");

            context.addParameter(step.rasHostPort(), "--ras");
            context.addParameter(step.dbName, "--db");

            context.addCredentialsEnv(step.clusterCredentialsID, VRunner.ENV_CLUSTER_USER, VRunner.ENV_CLUSTER_PWD);
        }
    }

    public static class SessionKillExecution extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient SessionStep step;

        protected SessionKillExecution(StepContext context, SessionStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) throws AbortException {
            context.setCommand("session");
            context.setCommand("kill");

            context.addParameter(step.rasHostPort(), "--ras");
            context.addParameter(step.dbName, "--db");
            context.addSwitch(step.killWithNoLock, "--with-nolock");

            context.addCredentialsEnv(step.clusterCredentialsID, VRunner.ENV_CLUSTER_USER, VRunner.ENV_CLUSTER_PWD);
        }
    }
}

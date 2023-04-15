package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class InitDevStep extends VRunner {

    @DataBoundSetter
    String src;

    @DataBoundSetter
    String dt;

    @DataBoundSetter
    Boolean dev;

    @DataBoundSetter
    Boolean storage;

    @DataBoundSetter
    String storageName;

    @DataBoundSetter
    String storageCredentialsID;

    @DataBoundSetter
    Integer storageVer;

    @DataBoundSetter
    Boolean v1;

    @DataBoundSetter
    Boolean v2;

    @DataBoundConstructor
    public InitDevStep() {
        super();
    }

    @Override
    public StepExecution start(StepContext context) {
        return new StepExecutionImpl(context, this);
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerInitDev";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("InitDevStep.DisplayName");
        }
    }

    public static class StepExecutionImpl extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient InitDevStep step;

        protected StepExecutionImpl(StepContext context, InitDevStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) throws AbortException {

            context.setCommand("init-dev");
            context.addParameter(step.src, "--src");
            context.addParameter(step.dt, "--dt");
            context.addSwitch(step.dev, "--dev");
            context.addSwitch(step.storage, "--storage");
            context.addParameter(step.storageName, "--storage-name");
            argStorageVer(context);
            context.addSwitch(step.v1, "--v1");
            context.addSwitch(step.v2, "--v2");

            context.addCredentialsEnv(step.storageCredentialsID, VRunner.ENV_STORAGE_USER, VRunner.ENV_STORAGE_PWD);
        }

        private void argStorageVer(VRunnerContext context) {
            var storageVer = step.storageVer;
            if (storageVer == null || storageVer == 0) {
                return;
            }
            context.addParameter(storageVer, "--storage-ver");
        }
    }
}

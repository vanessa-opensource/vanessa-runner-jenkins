package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class InitDevStep extends VRunner {

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String src;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String dt;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private Boolean dev;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private Boolean storage;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String storageName;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String storageCredentialsID;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private Integer storageVer;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private Boolean v1;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private Boolean v2;

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
            context.addParameter(step.getSrc(), "--src");
            context.addParameter(step.getDt(), "--dt");
            context.addSwitch(step.getDev(), "--dev");
            context.addSwitch(step.getStorage(), "--storage");
            context.addParameter(step.getStorageName(), "--storage-name");
            argStorageVer(context);
            context.addSwitch(step.getV1(), "--v1");
            context.addSwitch(step.getV2(), "--v2");

            context.addCredentialsEnv(step.getStorageCredentialsID(), VRunner.ENV_STORAGE_USER, VRunner.ENV_STORAGE_PWD);
        }

        private void argStorageVer(VRunnerContext context) {
            var storageVer = step.getStorageVer();
            if (storageVer == null || storageVer == 0) {
                return;
            }
            context.addParameter(storageVer, "--storage-ver");
        }
    }
}

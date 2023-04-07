package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import lombok.Getter;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class RunStep extends VRunner {

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String command;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String execute;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private Boolean noWait;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String onlineFile;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String exitCodePath;

    @DataBoundConstructor
    public RunStep() {
        super();
    }

    @Override
    public StepExecution start(StepContext context) {
        return new StepExecutionImpl(context, this);
    }

    public static class StepExecutionImpl extends VRunnerExecution {

        private static final long serialVersionUID = 1L;

        private final transient RunStep step;

        protected StepExecutionImpl(StepContext context, RunStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) {
            context.setCommand("run");
            context.addParameter(step.getCommand(), "--command");
            context.addParameter(step.getExecute(), "--execute");
            context.addSwitch(step.getNoWait(), "--no-wait");
            context.addParameter(step.getOnlineFile(), "--online-file");
            context.addParameter(step.getExitCodePath(), "--exitCodePath");
        }
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerRun";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("RunStep.DisplayName");
        }
    }
}

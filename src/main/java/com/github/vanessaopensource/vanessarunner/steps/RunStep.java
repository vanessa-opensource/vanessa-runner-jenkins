package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class RunStep extends VRunner {

    @DataBoundSetter
    String command;

    @DataBoundSetter
    String execute;

    @DataBoundSetter
    Boolean noWait;

    @DataBoundSetter
    String onlineFile;

    @DataBoundSetter
    String exitCodePath;

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
            context.addParameter(step.command, "--command");
            context.addParameter(step.execute, "--execute");
            context.addSwitch(step.noWait, "--no-wait");
            context.addParameter(step.onlineFile, "--online-file");
            context.addParameter(step.exitCodePath, "--exitCodePath");
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

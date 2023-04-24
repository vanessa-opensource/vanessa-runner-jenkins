package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;

public class LoadCfStep extends Load {

    @DataBoundConstructor
    public LoadCfStep() {
        super();
    }

    @Override
    public StepExecution start(StepContext context)  {
        return new LoadCfStep.StepExecutionImpl(context, this);
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerLoadCf";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("LoadCfStep.DisplayName");
        }
    }

    public static class StepExecutionImpl extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient LoadCfStep step;

        protected StepExecutionImpl(StepContext context, LoadCfStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) {

            context.setCommand("load");
            context.addParameter(step.file, "--src");
        }
    }

}

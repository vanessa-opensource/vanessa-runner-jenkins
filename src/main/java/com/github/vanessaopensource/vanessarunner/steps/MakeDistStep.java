package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;

public class MakeDistStep extends Compile {

    @DataBoundConstructor
    public MakeDistStep() {
        super();
    }

    @Override
    public StepExecution start(StepContext context)  {
        return new MakeDistStep.StepExecutionImpl(context, this);
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerMakeDist";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("MakeDistStep.DisplayName");
        }
    }

    public static class StepExecutionImpl extends Compile.StepExecutionImpl {
        private static final long serialVersionUID = 1L;

        private final transient MakeDistStep step;

        protected StepExecutionImpl(StepContext context, MakeDistStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) {
            context.setCommand("make-dist");
            context.setCommand(step.out);
            context.addParameter(step.src, "--src");
            context.addSwitch(step.current, "--current");

            addBuildNumber(context);
        }
    }
}

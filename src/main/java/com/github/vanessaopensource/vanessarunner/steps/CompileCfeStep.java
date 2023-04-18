package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;

public class CompileCfeStep extends Compile {

    @DataBoundConstructor
    public CompileCfeStep() {
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
            return "vrunnerCompileCfe";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("CompileCfeStep.DisplayName");
        }
    }

    public static class StepExecutionImpl extends Compile.StepExecutionImpl {

        protected StepExecutionImpl(StepContext context, CompileCfeStep step) {
            super(context, step);
        }

        @Override
        public void addCommandContext(VRunnerContext context) {
            context.setCommand("compileexttocfe");
            super.addCommandContext(context);
        }
    }
}

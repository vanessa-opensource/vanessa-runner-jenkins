package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;

public class CompileCfStep extends Compile {
    @DataBoundConstructor
    public CompileCfStep() {
        super();
    }
    @Override
    public StepExecution start(StepContext context) {
        return new CompileCfStep.StepExecutionImpl(context, this);
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerCompileCf";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("CompileCfStep.DisplayName");
        }
    }

    public static class StepExecutionImpl extends Compile.StepExecutionImpl {

        protected StepExecutionImpl(StepContext context, CompileCfStep step) {
            super(context, step);
        }

        @Override
        public void addCommandContext(VRunnerContext context) {
            context.setCommand("compile");
            super.addCommandContext(context);
        }
    }
}

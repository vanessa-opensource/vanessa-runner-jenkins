package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class CompileCfeStep extends VRunner {

    @DataBoundSetter
    String src;

    @DataBoundSetter
    String out;

    @DataBoundSetter
    Boolean current;

    @DataBoundSetter
    Integer buildNumber;

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

    public static class StepExecutionImpl extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient CompileCfeStep step;

        protected StepExecutionImpl(StepContext context, CompileCfeStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) {

            context.setCommand("compileexttocfe");
            context.addParameter(step.src, "--src");
            context.addParameter(step.out, "--out");
            context.addSwitch(step.current, "--current");
            context.addParameter(step.buildNumber, "--build-number");
        }
    }
}

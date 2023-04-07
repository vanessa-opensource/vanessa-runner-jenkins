package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import lombok.Getter;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class CompileCfeStep extends VRunner {

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String src;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private String out;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private Boolean current;

    @Getter
    @DataBoundSetter
    @SuppressWarnings("unused")
    private Integer buildNumber;

    @DataBoundConstructor
    @SuppressWarnings("unused")
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
            context.addParameter(step.getSrc(), "--src");
            context.addParameter(step.getOut(), "--out");
            context.addSwitch(step.getCurrent(), "--current");
            context.addParameter(step.getBuildNumber(), "--build-number");
        }
    }
}

package com.github.vanessaopensource.vanessarunner.steps;

import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.kohsuke.stapler.DataBoundSetter;

public abstract class Compile extends VRunner {
    @DataBoundSetter
    String src;

    @DataBoundSetter
    String out;

    @DataBoundSetter
    Boolean current;

    @DataBoundSetter
    Integer buildNumber;

    public abstract static class StepExecutionImpl extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient Compile step;

        public StepExecutionImpl(StepContext context, Compile step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) {
            context.addParameter(step.src, "--src");
            context.addParameter(step.out, "--out");
            context.addSwitch(step.current, "--current");
            context.addParameter(step.buildNumber, "--build-number");
        }
    }
}

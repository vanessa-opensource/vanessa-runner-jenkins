package com.github.vanessaopensource.vanessarunner.steps;

import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.kohsuke.stapler.DataBoundSetter;

public abstract class Compile extends VRunner {
    @DataBoundSetter
    String src = "";

    @DataBoundSetter
    String out = "";

    @DataBoundSetter
    Boolean current = false;

    @DataBoundSetter
    Integer buildNumber = 0;

    @DataBoundSetter
    Boolean withBuildNumber = false;

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

            addBuildNumber(context);
        }

        void addBuildNumber(VRunnerContext context) {
            if(step.withBuildNumber) {
                var envBuildNumber = context.getBuildNumber();
                context.addParameter(envBuildNumber, "--build-number");
            } else {
                context.addParameter(step.buildNumber, "--build-number");
            }
        }
    }
}

package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.Result;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;

public class AddStep extends RunTests {

    @DataBoundConstructor
    public AddStep() {
        super();
    }

    @Override
    public StepExecution start(StepContext context)  {
        return new AddStep.StepExecutionImpl(context, this);
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerAdd";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("AddStep.DisplayName");
        }
    }

    public static class StepExecutionImpl extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient AddStep step;

        protected StepExecutionImpl(StepContext context, AddStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) {

            exitCodes.put(1, Result.UNSTABLE);
            exitCodes.put(2, Result.UNSTABLE);

            context.setCommand("vanessa");
            context.addParameter(step.testsPath, "--path");

            if(!step.reportAllure.isBlank()) {
                context.setEnvVar("VANESSA_allurecreatereport", "true");
                context.setEnvVar("VANESSA_allurepath", step.reportAllure);
            }

            if(!step.reportJUnit.isBlank()) {
                context.setEnvVar("VANESSA_junitcreatereport", "true");
                context.setEnvVar("VANESSA_junitpath", step.reportJUnit);
            }
        }
    }
}

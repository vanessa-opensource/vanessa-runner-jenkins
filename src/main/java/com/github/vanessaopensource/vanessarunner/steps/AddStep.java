package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import hudson.model.Result;
import org.kohsuke.stapler.DataBoundConstructor;

public class AddStep extends RunTests {

    @DataBoundConstructor
    public AddStep() {
        super();
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {

        context.putExitCodeResult(1, Result.UNSTABLE);
        context.putExitCodeResult(2, Result.UNSTABLE);

        context.setCommand("vanessa");
        context.addParameter(testsPath, "--path");

        if(!reportAllure.isBlank()) {
            context.setEnvVar("VANESSA_allurecreatereport", "true");
            context.setEnvVar("VANESSA_allurepath", reportAllure);
        }

        if(!reportJUnit.isBlank()) {
            context.setEnvVar("VANESSA_junitcreatereport", "true");
            context.setEnvVar("VANESSA_junitpath", reportJUnit);
        }

        super.setCommandContext(context);
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
}

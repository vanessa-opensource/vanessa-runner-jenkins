package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.vrunner.Messages;
import com.github.vanessaopensource.vanessarunner.vrunner.RunTests;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunner;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunnerContext;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import hudson.model.Result;
import org.kohsuke.stapler.DataBoundConstructor;

public final class AddStep extends RunTests {

    @DataBoundConstructor
    public AddStep() {
        super();
    }

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {

        context.putExitCodeResult(1, Result.UNSTABLE);
        context.putExitCodeResult(2, Result.UNSTABLE);

        context.setCommand("vanessa");
        context.addParameter(getTestsPath(), "--path");

        setReportEnv(context, getReportAllure(), "allurecreatereport", "allurepath");
        setReportEnv(context, getReportJUnit(), "junitcreatereport", "junitpath");

        super.setCommandContext(context);
    }

    private void setReportEnv(final VRunnerContext context, final String reportPath,
                              final String envReportCreate, final String envReportPath) {
        if (reportPath.isBlank()) {
            return;
        }
        context.setEnvVar(vanessaEnvKey(envReportCreate), "true");
        context.setEnvVar(vanessaEnvKey(envReportPath), reportPath);
    }

    private String vanessaEnvKey(final String key) {
        return String.format("VANESSA_%s", key);
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

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

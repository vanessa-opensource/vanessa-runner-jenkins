package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.Messages;
import com.github.vanessaopensource.vanessarunner.steps.core.RunTests;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunner;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerContext;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import hudson.model.Result;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public final class XUnitStep extends RunTests {

    @DataBoundSetter
    private Boolean configTests = false;

    @DataBoundConstructor
    public XUnitStep() {
        super();
    }

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {

        context.putExitCodeResult(1, Result.UNSTABLE);
        context.putExitCodeResult(2, Result.NOT_BUILT);

        context.setCommand("xunit");
        context.setCommand(getTestsPath());
        context.addSwitch(configTests, "--config-tests");

        addArgReportsXUnit(context);
        addExitCodePath(context);

        super.setCommandContext(context);
    }

    private void addArgReportsXUnit(final  VRunnerContext context) {
        val reportsXUnit = new ArrayList<String>();

        addReport(reportsXUnit, "AllureXMLВерсия2", getReportAllure());
        addReport(reportsXUnit, "JUnitXML", getReportJUnit());

        if (!reportsXUnit.isEmpty()) {
            context.addParameter(String.join(";", reportsXUnit), "--reportsxunit");
        }
    }

    private void addReport(final List<String> reports, final String reportName, final String reportPath) {
        if (reportPath.isBlank()) {
            return;
        }
        reports.add(String.format("GenerateReport%s{%s}", reportName, reportPath));
    }

    private void addExitCodePath(final VRunnerContext context) throws AbortException {
        val exitCodeFile = context.createTempFile("xdd_", ".run");
        context.addParameter(exitCodeFile.getRemote(), "--xddExitCodePath");
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerXUnit";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("XUnitStep.DisplayName");
        }
    }
}

package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import hudson.model.Result;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.ArrayList;

public class XUnitStep extends RunTests {

    @DataBoundSetter
    Boolean configTests = false;

    @DataBoundConstructor
    public XUnitStep() {
        super();
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {

        context.putExitCodeResult(1, Result.UNSTABLE);
        context.putExitCodeResult(2, Result.NOT_BUILT);

        context.setCommand("xunit");
        context.setCommand(testsPath);
        context.addSwitch(configTests, "--config-tests");

        addArgReportsXUnit(context);
        addExitCodePath(context);

        super.setCommandContext(context);
    }

    private void addArgReportsXUnit(VRunnerContext context) {
        var reportsXUnit = new ArrayList<String>();
        if(!reportAllure.isBlank()) {
            reportsXUnit.add(String.format("GenerateReportAllureXMLВерсия2{%s}", reportAllure));
        }
        if(!reportJUnit.isBlank()) {
            reportsXUnit.add(String.format("GenerateReportJUnitXML{%s}", reportJUnit));
        }

        if(!reportsXUnit.isEmpty()) {
            context.addParameter(String.join(";", reportsXUnit), "--reportsxunit");
        }
    }

    private void addExitCodePath(VRunnerContext context) throws AbortException {
        try {
            var exitCodeFile = context.createTempFile("xdd_", ".run");
            context.addParameter(exitCodeFile.getRemote(), "--xddExitCodePath");
        } catch (Exception ex) {
            throw new AbortException(ex.getLocalizedMessage());
        }
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

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

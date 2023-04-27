package com.github.vanessaopensource.vanessarunner.steps;

import com.google.common.base.Strings;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.Result;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
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

    @Override
    public StepExecution start(StepContext context)  {
        return new StepExecutionImpl(context, this);
    }

    public static class StepExecutionImpl extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient XUnitStep step;

        protected StepExecutionImpl(StepContext context, XUnitStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) {
            context.setCommand("xunit");
            context.setCommand(step.testsPath);
            context.addSwitch(step.configTests, "--config-tests");
            addArgReportsXUnit(context);

            try {
                var exitCodeFile = context.createTempFile("xdd_", ".run");
                context.addParameter(exitCodeFile.getRemote(), "--xddExitCodePath");
                exitCodes.put(1, Result.UNSTABLE);
                exitCodes.put(2, Result.NOT_BUILT);
            } catch (Exception ex) {
                context.getLogger().println(ex.getLocalizedMessage());
            }
        }

        private void addArgReportsXUnit(VRunnerContext context) {
            var reportsXUnit = new ArrayList<String>();
            if(!step.reportAllure.isBlank()) {
                reportsXUnit.add(String.format("GenerateReportAllureXMLВерсия2{%s}", step.reportAllure));
            }
            if(!step.reportJUnit.isBlank()) {
                reportsXUnit.add(String.format("GenerateReportJUnitXML{%s}", step.reportJUnit));
            }

            if(!reportsXUnit.isEmpty()) {
                context.addParameter(String.join(";", reportsXUnit), "--reportsxunit");
            }
        }
    }
}

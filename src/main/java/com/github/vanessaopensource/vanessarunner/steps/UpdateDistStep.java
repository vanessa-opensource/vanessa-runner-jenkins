package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import lombok.Getter;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class UpdateDistStep extends VRunner {

    @Getter
    @DataBoundSetter
    String file = "";

    @Getter
    @DataBoundSetter
    String mergeSettings = "";

    @Getter
    @DataBoundSetter
    Boolean includeObjectsByUnresolvedRefs = false;

    @Getter
    @DataBoundSetter
    Boolean clearUnresolvedRefs = false;

    @Getter
    @DataBoundSetter
    Boolean dumpListOfTwiceChangedProperties = false;

    @Getter
    @DataBoundSetter
    Boolean force = false;

    @DataBoundConstructor
    public UpdateDistStep() {
        super();
    }

    @Override
    public StepExecution start(StepContext context) throws Exception {
        return new UpdateCfExecution(context, this);
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerUpdateDist";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("UpdateDistStep.DisplayName");
        }
    }

    public static class UpdateCfExecution extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient UpdateDistStep step;

        protected UpdateCfExecution(StepContext context, UpdateDistStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) {
            context.setCommand("update");
            context.addParameter(step.file, "--src");
            context.addParameter(step.mergeSettings, "--update-settings");
            context.addSwitch(step.includeObjectsByUnresolvedRefs, "--IncludeObjectsByUnresolvedRefs");
            context.addSwitch(step.clearUnresolvedRefs, "--ClearUnresolvedRefs");
            context.addSwitch(step.dumpListOfTwiceChangedProperties, "--DumpListOfTwiceChangedProperties");
            context.addSwitch(step.force, "--force");
        }
    }
}

package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import lombok.Getter;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class UpdateDbStep extends VRunner {

    @Getter
    @DataBoundSetter
    String extension = "";

    @Getter
    @DataBoundSetter
    Boolean v1 = false;

    @Getter
    @DataBoundSetter
    Boolean v2 = false;

    @Getter
    @DataBoundSetter

    Boolean dynamic = false;
    @DataBoundConstructor
    
    public UpdateDbStep() {
        super();
    }

    @Override
    public StepExecution start(StepContext context) throws Exception {
        if(extension.isBlank()) {
            return new UpdateDbCfExecution(context, this);
        } else {
            return new UpdateDbCfeExecution(context, this);
        }
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerUpdateDb";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("UpdateDbStep.DisplayName");
        }
    }

    public static class UpdateDbCfExecution extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient UpdateDbStep step;

        protected UpdateDbCfExecution(StepContext context, UpdateDbStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) {
            context.setCommand("updatedb");
            context.addSwitch(step.v1, "--v1");
            context.addSwitch(step.v2, "--v2");
            context.addSwitch(step.dynamic, "--dynamic");
        }
    }

    public static class UpdateDbCfeExecution extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient UpdateDbStep step;

        protected UpdateDbCfeExecution(StepContext context, UpdateDbStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) {
             context.setCommand("updateext");
             context.setCommand(step.extension);
        }
    }
}

package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
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
    public void setCommandContext(VRunnerContext context) throws AbortException {
        if(extension.isBlank()) {
            setUpdateDbCfContext(context);
        } else {
            setUpdateDbCfeContext(context);
        }

        super.setCommandContext(context);
    }

    private void setUpdateDbCfContext(VRunnerContext context) {
        context.setCommand("updatedb");
        context.addSwitch(v1, "--v1");
        context.addSwitch(v2, "--v2");
        context.addSwitch(dynamic, "--dynamic");
    }

    private void setUpdateDbCfeContext(VRunnerContext context) {
        context.setCommand("updateext");
        context.setCommand(extension);
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
}

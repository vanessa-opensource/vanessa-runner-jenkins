package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.Messages;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunner;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerContext;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerInfobase;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public final class UpdateDbStep extends VRunnerInfobase {

    @DataBoundSetter
    private String extension = "";

    @DataBoundSetter
    private Boolean v1 = false;

    @DataBoundSetter
    private Boolean v2 = false;

    @DataBoundSetter
    private Boolean dynamic = false;

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
    public static final class DescriptorImpl extends VRunner.Descriptor {

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

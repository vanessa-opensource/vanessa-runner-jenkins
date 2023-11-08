package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.vrunner.Messages;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunner;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunnerContext;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunnerInfobase;
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

    @DataBoundSetter
    private Boolean ibcmd = false;

    @DataBoundConstructor
    public UpdateDbStep() {
        super();
    }

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {
        if (extension.isBlank()) {
            setUpdateDbCfContext(context);
        } else {
            setUpdateDbCfeContext(context);
        }

        super.setCommandContext(context);
    }

    private void setUpdateDbCfContext(final VRunnerContext context) {
        context.setCommand("updatedb");
        context.addSwitch(v1, "--v1");
        context.addSwitch(v2, "--v2");
        context.addSwitch(dynamic, "--dynamic");
        context.addSwitch(ibcmd, "--ibcmd");
    }

    private void setUpdateDbCfeContext(final VRunnerContext context) {
        context.setCommand("updateext");
        context.setCommand(extension);
        context.addSwitch(ibcmd, "--ibcmd");
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

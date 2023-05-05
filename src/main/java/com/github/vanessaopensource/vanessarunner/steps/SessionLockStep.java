package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.*;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public final class SessionLockStep extends VRunnerRAC {

    @DataBoundSetter
    private String ucCode = "";

    @DataBoundSetter
    private String lockMessage = "";

    @DataBoundSetter
    private Integer lockStartAt = 0;

    @DataBoundSetter
    private Boolean lockEndClear = false;

    @DataBoundConstructor
    public SessionLockStep(String dbName) {
        super(dbName);
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {
        context.setCommand("session");
        context.setCommand("lock");
        context.addParameter(ucCode, "--uccode");
        context.addParameter(lockMessage, "--lockmessage");
        context.addParameter(lockStartAt, "--lockstartat");
        context.addSwitch(lockEndClear, "--lockendclear");

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerSessionLock";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("SessionLockStep.DisplayName");
        }
    }
}

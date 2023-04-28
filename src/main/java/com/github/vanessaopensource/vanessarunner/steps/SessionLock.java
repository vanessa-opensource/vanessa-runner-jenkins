package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class SessionLock extends Session {

    @Getter
    @DataBoundSetter
    String lockMessage = "";

    @Getter
    @DataBoundSetter
    Integer lockStartAt = 0;

    @Getter
    @DataBoundSetter
    Boolean lockEndClear = false;

    @DataBoundConstructor
    public SessionLock() {
        super();
    }

    @Override
    public void addCommandContext(VRunnerContext context) throws AbortException {
        context.setCommand("session");
        context.setCommand("lock");
        context.addParameter(lockMessage, "--lockmessage");
        context.addParameter(lockStartAt, "--lockstartat");
        context.addSwitch(lockEndClear, "--lockendclear");

        super.addCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

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

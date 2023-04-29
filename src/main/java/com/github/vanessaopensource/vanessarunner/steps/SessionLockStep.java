package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.Messages;
import com.github.vanessaopensource.vanessarunner.steps.core.Session;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunner;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerContext;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public class SessionLockStep extends Session {

    @DataBoundSetter
    String lockMessage = "";

    @DataBoundSetter
    Integer lockStartAt = 0;

    @DataBoundSetter
    Boolean lockEndClear = false;

    @DataBoundConstructor
    public SessionLockStep() {
        super();
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {
        context.setCommand("session");
        context.setCommand("lock");
        context.addParameter(lockMessage, "--lockmessage");
        context.addParameter(lockStartAt, "--lockstartat");
        context.addSwitch(lockEndClear, "--lockendclear");

        super.setCommandContext(context);
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

package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class SessionKillStep extends Session {

    @Getter
    @DataBoundSetter
    Boolean killWithNoLock = false;

    @DataBoundConstructor
    public SessionKillStep() {
        super();
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {
        context.setCommand("session");
        context.setCommand("kill");

        context.addSwitch(killWithNoLock, "--with-nolock");

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerSessionKill";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("SessionKillStep.DisplayName");
        }
    }
}

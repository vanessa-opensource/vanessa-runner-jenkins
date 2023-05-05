package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.Messages;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunner;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerContext;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerRAC;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public final class SessionKillStep extends VRunnerRAC {

    @DataBoundSetter
    private Boolean killWithNoLock = false;

    @DataBoundConstructor
    public SessionKillStep(final String dbName) {
        super(dbName);
    }

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {
        context.setCommand("session");
        context.setCommand("kill");

        context.addSwitch(killWithNoLock, "--with-nolock");

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

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

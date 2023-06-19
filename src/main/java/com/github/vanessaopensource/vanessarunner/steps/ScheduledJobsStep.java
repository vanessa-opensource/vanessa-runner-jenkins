package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.vrunner.Messages;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunner;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunnerContext;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunnerRAC;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundConstructor;

@Getter
@Setter
public final class ScheduledJobsStep extends VRunnerRAC {

    private final Boolean lock;

    @DataBoundConstructor
    public ScheduledJobsStep(final Boolean lock, final String dbName) {
        super(dbName);
        this.lock = lock;
    }

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {

        context.setCommand("scheduledjobs");
        if (lock) {
            context.setCommand("lock");
        } else {
            context.setCommand("unlock");
        }

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerScheduledJobs";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("ScheduledJobsStep.DisplayName");
        }
    }
}

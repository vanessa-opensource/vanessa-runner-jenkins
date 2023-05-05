package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.*;
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
    public ScheduledJobsStep(Boolean lock, String dbName) {
        super(dbName);
        this.lock = lock;
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {

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

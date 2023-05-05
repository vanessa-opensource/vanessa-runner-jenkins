package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.Load;
import com.github.vanessaopensource.vanessarunner.steps.core.Messages;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunner;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerContext;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import org.kohsuke.stapler.DataBoundConstructor;

public final class LoadCfStep extends Load {

    @DataBoundConstructor
    public LoadCfStep() {
        super();
    }

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {
        context.setCommand("load");
        context.addParameter(getFile(), "--src");

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerLoadCf";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("LoadCfStep.DisplayName");
        }
    }
}

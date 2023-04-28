package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import org.kohsuke.stapler.DataBoundConstructor;

public class LoadCfStep extends Load {

    @DataBoundConstructor
    public LoadCfStep() {
        super();
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {
        context.setCommand("load");
        context.addParameter(file, "--src");

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

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

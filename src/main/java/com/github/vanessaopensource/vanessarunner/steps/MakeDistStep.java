package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import org.kohsuke.stapler.DataBoundConstructor;

public class MakeDistStep extends Compile {

    @DataBoundConstructor
    public MakeDistStep() {
        super();
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {
        context.setCommand("make-dist");
        context.setCommand(out);
        context.addParameter(src, "--src");

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerMakeDist";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("MakeDistStep.DisplayName");
        }
    }
}

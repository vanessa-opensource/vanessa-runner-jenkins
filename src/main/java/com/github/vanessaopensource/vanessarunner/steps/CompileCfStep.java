package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import org.kohsuke.stapler.DataBoundConstructor;

public class CompileCfStep extends Compile {

    @DataBoundConstructor
    public CompileCfStep() {
        super();
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {
        context.setCommand("compile");
        context.addParameter(src, "--src");
        context.addParameter(out, "--out");

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerCompileCf";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("CompileCfStep.DisplayName");
        }
    }
}

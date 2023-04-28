package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import org.kohsuke.stapler.DataBoundConstructor;

public class CompileCfeStep extends Compile {

    @DataBoundConstructor
    public CompileCfeStep() {
        super();
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {
        context.setCommand("compileexttocfe");
        context.addParameter(src, "--src");
        context.addParameter(out, "--out");

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerCompileCfe";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("CompileCfeStep.DisplayName");
        }
    }
}

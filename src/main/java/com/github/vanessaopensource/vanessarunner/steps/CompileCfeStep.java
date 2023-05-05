package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.Compile;
import com.github.vanessaopensource.vanessarunner.steps.core.Messages;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunner;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerContext;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import org.kohsuke.stapler.DataBoundConstructor;

public final class CompileCfeStep extends Compile {

    @DataBoundConstructor
    public CompileCfeStep() {
        super();
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {
        context.setCommand("compileexttocfe");
        context.addParameter(getSrc(), "--src");
        context.addParameter(getOut(), "--out");

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

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

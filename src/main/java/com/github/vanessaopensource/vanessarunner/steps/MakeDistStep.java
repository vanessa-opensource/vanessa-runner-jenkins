package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.vrunner.Compile;
import com.github.vanessaopensource.vanessarunner.vrunner.Messages;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunner;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunnerContext;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import org.kohsuke.stapler.DataBoundConstructor;

public final class MakeDistStep extends Compile {

    @DataBoundConstructor
    public MakeDistStep() {
        super();
    }

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {
        context.setCommand("make-dist");
        context.setCommand(getOut());
        context.addParameter(getSrc(), "--src");

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

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

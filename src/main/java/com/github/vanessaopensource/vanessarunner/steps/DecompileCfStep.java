package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.vrunner.Decompile;
import com.github.vanessaopensource.vanessarunner.vrunner.Messages;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunner;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunnerContext;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import org.kohsuke.stapler.DataBoundConstructor;

public final class DecompileCfStep extends Decompile {

    @DataBoundConstructor
    public DecompileCfStep() {
        super();
    }

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {

        context.setCommand("decompile");
        context.addParameter(getOut(), "--out");
        context.setCommand("--current");

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerDecompileCf";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("DecompileCfStep.DisplayName");
        }
    }
}

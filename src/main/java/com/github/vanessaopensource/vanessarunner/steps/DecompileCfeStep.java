package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.vrunner.Decompile;
import com.github.vanessaopensource.vanessarunner.vrunner.Messages;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunner;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunnerContext;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public final class DecompileCfeStep extends Decompile {

    @DataBoundSetter
    private String extension = "";

    @DataBoundConstructor
    public DecompileCfeStep() {
        super();
    }

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {

        context.setCommand("decompileext");
        context.setCommand(extension);
        context.setCommand(getOut());

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerDecompileCfe";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("DecompileCfeStep.DisplayName");
        }
    }
}

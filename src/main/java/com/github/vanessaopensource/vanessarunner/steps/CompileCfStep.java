package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.vrunner.Compile;
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
public final class CompileCfStep extends Compile {

    @DataBoundSetter
    private Boolean ibcmd = false;

    @DataBoundConstructor
    public CompileCfStep() {
        super();
    }

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {
        context.setCommand("compile");
        context.addParameter(getSrc(), "--src");
        context.addParameter(getOut(), "--out");
        context.addSwitch(ibcmd, "--ibcmd");

        if(ibcmd) {
            context.setNonInteractive();
        }

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

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

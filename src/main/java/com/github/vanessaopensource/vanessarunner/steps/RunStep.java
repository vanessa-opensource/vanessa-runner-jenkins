package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.vrunner.Messages;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunner;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunnerContext;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunnerInfobase;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public final class RunStep extends VRunnerInfobase {

    @DataBoundSetter
    private String command = "";

    @DataBoundSetter
    private String execute = "";

    @DataBoundSetter
    private Boolean noWait = false;

    @DataBoundSetter
    private String onlineFile = "";

    @DataBoundSetter
    private String exitCodePath = "";

    @DataBoundConstructor
    public RunStep() {
        super();
    }

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {
        context.setCommand("run");
        context.addParameter(command, "--command");
        context.addParameter(getExecute(), "--execute");
        context.addSwitch(noWait, "--no-wait");
        context.addParameter(onlineFile, "--online-file");
        context.addParameter(exitCodePath, "--exitCodePath");

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerRun";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("RunStep.DisplayName");
        }
    }
}

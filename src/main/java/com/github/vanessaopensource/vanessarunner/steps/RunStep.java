package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.Messages;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunner;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerContext;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerInfobase;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public class RunStep extends VRunnerInfobase {

    @DataBoundSetter
    String command = "";

    @DataBoundSetter
    String execute = "";

    @DataBoundSetter
    Boolean noWait = false;

    @DataBoundSetter
    String onlineFile = "";

    @DataBoundSetter
    String exitCodePath = "";

    @DataBoundConstructor
    public RunStep() {
        super();
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {
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
    public static class DescriptorImpl extends VRunner.Descriptor {

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

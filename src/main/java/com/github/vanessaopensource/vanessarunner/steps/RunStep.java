package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class RunStep extends VRunner {

    @DataBoundSetter
    String command;

    @DataBoundSetter
    String execute;

    @DataBoundSetter
    Boolean noWait;

    @DataBoundSetter
    String onlineFile;

    @DataBoundSetter
    String exitCodePath;

    @DataBoundConstructor
    public RunStep() {
        super();
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {
        context.setCommand("run");
        context.addParameter(command, "--command");
        context.addParameter(execute, "--execute");
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

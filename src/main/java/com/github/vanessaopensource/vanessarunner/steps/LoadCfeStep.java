package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class LoadCfeStep extends Load {

    @Getter
    @DataBoundSetter
    String extension = "";

    @Getter
    @DataBoundSetter
    String src = "";

    @Getter
    @DataBoundSetter
    Boolean updateDb = true;

    @DataBoundConstructor
    public LoadCfeStep() {
        super();
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {
        if(src.isBlank()) {
            setLoadCfeFileContext(context);
        } else {
            setLoadCfeSrcContext(context);
        }

        super.setCommandContext(context);
    }

    private void setLoadCfeFileContext(VRunnerContext context) {
        context.setCommand("loadext");
        context.addParameter(file, "--file");
        context.addParameter(extension, "--extension");
        context.addSwitch(updateDb, "--updatedb");
    }

    private void setLoadCfeSrcContext(VRunnerContext context) {
        context.setCommand("compileext");
        context.setCommand(src);
        context.setCommand(extension);
        context.addSwitch(updateDb, "--updatedb");
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerLoadCfe";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("LoadCfeStep.DisplayName");
        }
    }
}

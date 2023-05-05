package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.Load;
import com.github.vanessaopensource.vanessarunner.steps.core.Messages;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunner;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerContext;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public class LoadCfeStep extends Load {

    @DataBoundSetter
    private String extension = "";

    @DataBoundSetter
    private String src = "";

    @DataBoundSetter
    private Boolean updateDb = true;

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
        context.addParameter(getFile(), "--file");
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

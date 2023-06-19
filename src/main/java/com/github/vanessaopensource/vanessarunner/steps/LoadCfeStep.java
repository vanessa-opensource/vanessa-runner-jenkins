package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.vrunner.Load;
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
public final class LoadCfeStep extends Load {

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
    public void setCommandContext(final VRunnerContext context) throws AbortException {
        if (src.isBlank()) {
            setLoadCfeFileContext(context);
        } else {
            setLoadCfeSrcContext(context);
        }

        super.setCommandContext(context);
    }

    private void setLoadCfeFileContext(final VRunnerContext context) {
        context.setCommand("loadext");
        context.addParameter(getFile(), "--file");
        context.addParameter(extension, "--extension");
        context.addSwitch(updateDb, "--updatedb");
    }

    private void setLoadCfeSrcContext(final VRunnerContext context) {
        context.setCommand("compileext");
        context.setCommand(src);
        context.setCommand(extension);
        context.addSwitch(updateDb, "--updatedb");
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

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

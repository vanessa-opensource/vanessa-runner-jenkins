package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import lombok.Getter;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class LoadCfeStep extends Load {

    @DataBoundSetter
    String extension = "";

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
    public StepExecution start(StepContext context)  {
        if(src.isBlank()) {
            return new LoadCfeFileExecution(context, this);
        } else {
            return new LoadCfeSrcExecution(context, this);
        }
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

    public static class LoadCfeFileExecution extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient LoadCfeStep step;

        protected LoadCfeFileExecution(StepContext context, LoadCfeStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) {
            context.setCommand("loadext");
            context.addParameter(step.file, "--file");
            context.addParameter(step.extension, "--extension");
            context.addSwitch(step.updateDb, "--updatedb");
        }
    }

    public static class LoadCfeSrcExecution extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient LoadCfeStep step;

        protected LoadCfeSrcExecution(StepContext context, LoadCfeStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) {
            context.setCommand("compileext");
            context.setCommand(step.src);
            context.setCommand(step.extension);
            context.addSwitch(step.updateDb, "--updatedb");
        }
    }
}

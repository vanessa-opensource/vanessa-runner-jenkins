package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class LoadCfeStep extends Load {

    @DataBoundSetter
    String extension = "";

    @DataBoundSetter
    String src = "";

    @DataBoundSetter
    Boolean updateDb = true;

    @DataBoundConstructor
    public LoadCfeStep() {
        super();
    }

    @Override
    public StepExecution start(StepContext context)  {
        return new LoadCfeStep.StepExecutionImpl(context, this);
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

    public static class StepExecutionImpl extends VRunnerExecution {
        private static final long serialVersionUID = 1L;

        private final transient LoadCfeStep step;

        protected StepExecutionImpl(StepContext context, LoadCfeStep step) {
            super(context, step);
            this.step = step;
        }

        @Override
        public void addCommandContext(VRunnerContext context) {
            if(!step.src.isBlank()) {
                context.setCommand("compileext");
                context.setCommand(step.src);
                context.setCommand(step.extension);
            } else {
                context.setCommand("loadext");
                context.addParameter(step.file, "--file");
                context.addParameter(step.extension, "--extension");
            }
            context.addSwitch(step.updateDb, "--updatedb");
        }
    }
}

package com.github.vanessaopensource.vanessarunner.steps;

import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.SynchronousNonBlockingStepExecution;

import java.io.IOException;

public class VRunnerExecution extends SynchronousNonBlockingStepExecution<Integer> {

    private transient final VRunner step;

    public VRunnerExecution(StepContext context, VRunner step) {
        super(context);
        this.step = step;
    }

    @Override
    public Integer run() throws Exception {

        var context = new VRunnerContext(getContext());

        step.setCommandContext(context);

        executeVRunner(context);

        context.cleanup();

        return 0;
    }

    private void executeVRunner(VRunnerContext context) throws IOException, InterruptedException {

        var exitCode = context.createStarter().join();

        if (exitCode != 0) {
            context.verifyExitCode(exitCode);
        }
    }
}

package com.github.vanessaopensource.vanessarunner.steps;

import hudson.AbortException;

import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.SynchronousNonBlockingStepExecution;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

abstract public class VRunnerExecution extends SynchronousNonBlockingStepExecution<Integer> {

    private transient final VRunner step;

    protected final Set<Integer> ignoredExitCodes = new HashSet<>();

    public VRunnerExecution(StepContext context, VRunner step) {
        super(context);
        this.step = step;
    }

    abstract void addCommandContext(VRunnerContext context) throws AbortException;

    @Override
    public Integer run() throws Exception {

        var context = new VRunnerContext(getContext());

        addCommandContext(context);

        context.addParameter(step.getIbConnection(), "--ibconnection");
        context.addParameter(step.getUcCode(), "--uccode");
        context.addParameter(step.getV8Version(), "--v8version");
        context.addSwitch(step.getNoCacheUse(), "--nocacheuse");
        context.addParameter(step.getAdditional(), "--additional");
        argOrdinaryApp(context);
        context.addParameter(step.getLanguage(), "--language");
        context.addParameter(step.getLocale(), "--locale");

        context.addCredentialsEnv(step.getDatabaseCredentialsID(), VRunner.ENV_DBUSER, VRunner.ENV_DBPWD);

        executeVRunner(context);

        return 0;
    }

    private void executeVRunner(VRunnerContext context) throws IOException, InterruptedException {

        var logger = context.getLogger();
        var exitCode = context.createStarter().join();

        if (exitCode == 0) {
            return;
        } else if (ignoredExitCodes.contains(exitCode)) {
            logger.format(Messages.getString("VRunnerExecution.RunIgnoredExitCode"), exitCode);
            return;
        } else {
            throw new AbortException(String.format(Messages.getString("VRunnerExecution.RunAborted"), exitCode));
        }
    }

    private void argOrdinaryApp(VRunnerContext context) {
        var ordinaryApp = step.getOrdinaryApp();
        if (ordinaryApp == null) {
            return;
        }

        String value;
        if(ordinaryApp) {
            value = "1";
        } else {
            value = "-1";
        }
        context.addParameter(value, "--ordinaryapp");
    }
}

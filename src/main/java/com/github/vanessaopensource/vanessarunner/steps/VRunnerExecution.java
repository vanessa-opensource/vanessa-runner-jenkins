package com.github.vanessaopensource.vanessarunner.steps;

import hudson.AbortException;

import hudson.model.Result;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.SynchronousNonBlockingStepExecution;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

abstract public class VRunnerExecution extends SynchronousNonBlockingStepExecution<Integer> {

    private transient final VRunner step;

    protected final Map<Integer, Result> exitCodes = new HashMap<>();

    public VRunnerExecution(StepContext context, VRunner step) {
        super(context);
        this.step = step;
    }

    abstract void addCommandContext(VRunnerContext context) throws AbortException;

    @Override
    public Integer run() throws Exception {

        var context = new VRunnerContext(getContext());

        addCommandContext(context);

        context.addParameter(step.ibConnection, "--ibconnection");
        context.addParameter(step.ucCode, "--uccode");
        context.addParameter(step.v8Version, "--v8version");
        context.addSwitch(step.noCacheUse, "--nocacheuse");
        context.addParameter(step.additional, "--additional");
        argOrdinaryApp(context);
        context.addParameter(step.language, "--language");
        context.addParameter(step.locale, "--locale");
        context.addParameter(step.settings, "--settings");

        context.addCredentialsEnv(step.databaseCredentialsID, VRunner.ENV_DBUSER, VRunner.ENV_DBPWD);

        executeVRunner(context);

        return 0;
    }

    private void executeVRunner(VRunnerContext context) throws IOException, InterruptedException {

        var exitCode = context.createStarter().join();

        if (exitCode == 0) {
            return;
        } else if (exitCodes.containsKey(exitCode)) {
            final var result = exitCodes.get(exitCode);
            context.setResult(result);
        } else {
            throw new AbortException(String.format(Messages.getString("VRunnerExecution.RunAborted"), exitCode));
        }
    }

    private void argOrdinaryApp(VRunnerContext context) {
        var ordinaryApp = step.ordinaryApp;
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

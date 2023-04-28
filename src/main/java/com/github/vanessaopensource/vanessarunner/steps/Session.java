package com.github.vanessaopensource.vanessarunner.steps;

import hudson.AbortException;
import lombok.Getter;
import org.kohsuke.stapler.DataBoundSetter;

public abstract class Session extends VRunner {

    @Getter
    @DataBoundSetter
    String rasHost = "";

    @Getter
    @DataBoundSetter
    Integer rasPort = 1545;

    @Getter
    @DataBoundSetter
    String dbName = "";

    @Getter
    @DataBoundSetter
    String clusterCredentialsID = "";

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {
        context.addParameter(rasHostPort(), "--ras");
        context.addParameter(dbName, "--db");
        context.addCredentialsEnv(clusterCredentialsID, VRunner.ENV_CLUSTER_USER, VRunner.ENV_CLUSTER_PWD);

        super.setCommandContext(context);
    }

    private String rasHostPort() {
        return String.format("%s:%d", rasHost, rasPort);
    }
}

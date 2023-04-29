package com.github.vanessaopensource.vanessarunner.steps.core;

import hudson.AbortException;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public abstract class Session extends VRunnerRAC {

    @DataBoundSetter
    String dbName = "";

    @DataBoundSetter
    String clusterCredentialsID = "";

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {
        context.addParameter(rasHostPort(), "--ras");
        context.addParameter(dbName, "--db");
        context.addCredentialsEnv(clusterCredentialsID, VRunner.ENV_CLUSTER_USER, VRunner.ENV_CLUSTER_PWD);

        super.setCommandContext(context);
    }
}

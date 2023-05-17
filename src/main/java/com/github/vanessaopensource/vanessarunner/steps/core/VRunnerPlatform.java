package com.github.vanessaopensource.vanessarunner.steps.core;

import hudson.AbortException;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public abstract class VRunnerPlatform extends VRunner {
    @DataBoundSetter
    private String databaseCredentialsID = "";

    @DataBoundSetter
    private String v8Version = "8.3";

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {
        context.addParameter(v8Version, "--v8version");
        context.addCredentialsEnv(databaseCredentialsID, VRunner.ENV_DBUSER, VRunner.ENV_DBPWD);

        super.setCommandContext(context);
    }
}

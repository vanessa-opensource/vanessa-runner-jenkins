package com.github.vanessaopensource.vanessarunner.steps.core;

import hudson.AbortException;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.Objects;

@Getter
@Setter
public abstract class VRunnerRAC extends VRunner {

    private static final Integer DEFAULT_RAS_PORT = 1545;
    private static final String DEFAULT_RAS_HOST = "localhost";

    private final String dbName;

    @DataBoundSetter
    private String rasHost = DEFAULT_RAS_HOST;

    @DataBoundSetter
    private Integer rasPort = DEFAULT_RAS_PORT;

    @DataBoundSetter
    private String clusterCredentialsID = "";

    protected VRunnerRAC(final String dbName) {
        assert !dbName.isBlank();
        this.dbName = dbName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {
        context.addParameter(rasHostPort(), "--ras");
        context.addParameter(dbName, "--db");
        context.addCredentialsEnv(clusterCredentialsID, VRunner.ENV_CLUSTER_USER, VRunner.ENV_CLUSTER_PWD);
    }

    private String rasHostPort() {

        if (Objects.equals(rasPort, DEFAULT_RAS_PORT)) {
            return rasHost;
        } else {
            return String.format("%s:%d", rasHost, rasPort);
        }
    }
}

package com.github.vanessaopensource.vanessarunner.steps.core;

import hudson.AbortException;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.Objects;

@Getter
@Setter
public abstract class VRunnerInfobase extends VRunner {

    private Integer DEFAULT_CLUSTER_PORT = 1541;

    @DataBoundSetter
    String ibConnection = "";

    @DataBoundSetter
    String ibPath = "";

    @DataBoundSetter
    String ibCluster = "";

    @DataBoundSetter
    Integer ibClusterPort = DEFAULT_CLUSTER_PORT;

    @DataBoundSetter
    String ibName = "";

    @DataBoundSetter
    String ucCode = "";

    @DataBoundSetter
    Boolean noCacheUse = false;

    @DataBoundSetter
    String additional = "";

    @DataBoundSetter
    Boolean ordinaryApp = false;

    @DataBoundSetter
    String language = "";

    @DataBoundSetter
    String locale = "";

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {

        context.addParameter(buildIbConnection(), "--ibconnection");
        context.addParameter(ucCode, "--uccode");
        context.addSwitch(noCacheUse, "--nocacheuse");
        context.addParameter(additional, "--additional");
        context.addSwitch(ordinaryApp, "--ordinaryapp", "1");
        context.addParameter(language, "--language");
        context.addParameter(locale, "--locale");

        super.setCommandContext(context);
    }

    private String buildIbConnection() {

        if(!ibConnection.isBlank()) {
            return ibConnection;
        } else if(!ibPath.isBlank()) {
            return String.format("/F%s", ibPath);
        } else if(!ibCluster.isBlank() && !ibName.isBlank()) {
            if(Objects.equals(ibClusterPort, DEFAULT_CLUSTER_PORT)) {
                return String.format("/S%s/%s", ibCluster, ibName);
            } else {
                return String.format("/S%s:%d/%s", ibCluster, ibClusterPort, ibName);
            }
        } else {
            return "";
        }
    }
}

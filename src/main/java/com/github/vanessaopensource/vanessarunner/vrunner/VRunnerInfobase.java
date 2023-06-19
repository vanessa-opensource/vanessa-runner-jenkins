package com.github.vanessaopensource.vanessarunner.vrunner;

import hudson.AbortException;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.Objects;

@Getter
@Setter
public abstract class VRunnerInfobase extends VRunnerPlatform {

    public static final Integer DEFAULT_CLUSTER_PORT = 1541;

    @DataBoundSetter
    private String ibConnection = "";

    @DataBoundSetter
    private String ibPath = "";

    @DataBoundSetter
    private String ibCluster = "";

    @DataBoundSetter
    private Integer ibClusterPort = DEFAULT_CLUSTER_PORT;

    @DataBoundSetter
    private String ibName = "";

    @DataBoundSetter
    private String ucCode = "";

    @DataBoundSetter
    private Boolean noCacheUse = false;

    @DataBoundSetter
    private String additional = "";

    @DataBoundSetter
    private Boolean ordinaryApp = false;

    @DataBoundSetter
    private String language = "";

    @DataBoundSetter
    private String locale = "";

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {

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

        if (!ibConnection.isBlank()) {
            return ibConnection;
        } else if (!ibPath.isBlank()) {
            return String.format("/F%s", ibPath);
        } else if (!ibCluster.isBlank() && !ibName.isBlank()) {
            if (Objects.equals(ibClusterPort, DEFAULT_CLUSTER_PORT)) {
                return String.format("/S%s/%s", ibCluster, ibName);
            } else {
                return String.format("/S%s:%d/%s", ibCluster, ibClusterPort, ibName);
            }
        } else {
            return "";
        }
    }
}

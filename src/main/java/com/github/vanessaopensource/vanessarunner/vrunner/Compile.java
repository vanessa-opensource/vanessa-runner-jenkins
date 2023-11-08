package com.github.vanessaopensource.vanessarunner.vrunner;

import hudson.AbortException;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public abstract class Compile extends VRunnerInfobase {

    @DataBoundSetter
    private String src = "";

    @DataBoundSetter
    private String out = "";

    @DataBoundSetter
    private  Boolean current = false;

    @DataBoundSetter
    private Integer buildNumber = 0;

    @DataBoundSetter
    private Boolean withBuildNumber = false;

    @DataBoundSetter
    private Boolean ibcmd = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {

        context.addSwitch(current, "--current");
        addBuildNumber(context);

        super.setCommandContext(context);
    }

    private void addBuildNumber(final VRunnerContext context) {
        if (withBuildNumber) {
            val envBuildNumber = context.getBuildNumber();
            context.addParameter(envBuildNumber, "--build-number");
        } else {
            context.addParameter(buildNumber, "--build-number");
        }
    }
}

package com.github.vanessaopensource.vanessarunner.steps;

import hudson.AbortException;
import org.kohsuke.stapler.DataBoundSetter;

public abstract class Compile extends VRunner {
    @DataBoundSetter
    String src = "";

    @DataBoundSetter
    String out = "";

    @DataBoundSetter
    Boolean current = false;

    @DataBoundSetter
    Integer buildNumber = 0;

    @DataBoundSetter
    Boolean withBuildNumber = false;

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {

        context.addSwitch(current, "--current");
        addBuildNumber(context);

        super.setCommandContext(context);
    }

    private void addBuildNumber(VRunnerContext context) {
        if(withBuildNumber) {
            var envBuildNumber = context.getBuildNumber();
            context.addParameter(envBuildNumber, "--build-number");
        } else {
            context.addParameter(buildNumber, "--build-number");
        }
    }
}

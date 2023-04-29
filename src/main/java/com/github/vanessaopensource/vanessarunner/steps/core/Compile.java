package com.github.vanessaopensource.vanessarunner.steps.core;

import hudson.AbortException;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public abstract class Compile extends VRunnerInfobase {

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

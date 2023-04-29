package com.github.vanessaopensource.vanessarunner.steps.core;

import hudson.AbortException;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public abstract class VRunnerInfobase extends VRunner {

    @DataBoundSetter
    String ibConnection = "";

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

        context.addParameter(ibConnection, "--ibconnection");
        context.addParameter(ucCode, "--uccode");
        context.addSwitch(noCacheUse, "--nocacheuse");
        context.addParameter(additional, "--additional");
        context.addSwitch(ordinaryApp, "--ordinaryapp", "1");
        context.addParameter(language, "--language");
        context.addParameter(locale, "--locale");

        super.setCommandContext(context);
    }
}

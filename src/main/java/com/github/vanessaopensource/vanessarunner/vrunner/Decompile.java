package com.github.vanessaopensource.vanessarunner.vrunner;

import hudson.AbortException;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public abstract class Decompile extends VRunnerInfobase {

    @DataBoundSetter
    private String out = "";

    @DataBoundSetter
    private Boolean ibcmd = false;

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {
        context.addSwitch(ibcmd, "--ibcmd");

        if(ibcmd) {
            context.setNonInteractive();
        }
        super.setCommandContext(context);
    }
}

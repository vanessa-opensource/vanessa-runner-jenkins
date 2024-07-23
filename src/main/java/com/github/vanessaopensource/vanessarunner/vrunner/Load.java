package com.github.vanessaopensource.vanessarunner.vrunner;

import hudson.AbortException;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public abstract class Load extends VRunnerInfobase {

    @DataBoundSetter
    private String file = "";

    @DataBoundSetter
    private Boolean ibcmd = false;

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {
        context.addSwitch(getIbcmd(), "--ibcmd");

        if(ibcmd) {
            context.setNonInteractive();
        }
        super.setCommandContext(context);
    }
}

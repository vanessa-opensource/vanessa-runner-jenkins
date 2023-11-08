package com.github.vanessaopensource.vanessarunner.vrunner;

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
}

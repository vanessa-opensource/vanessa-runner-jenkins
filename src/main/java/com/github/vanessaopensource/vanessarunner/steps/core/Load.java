package com.github.vanessaopensource.vanessarunner.steps.core;

import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public abstract class Load extends VRunnerInfobase {

    @DataBoundSetter
    private String file = "";
}

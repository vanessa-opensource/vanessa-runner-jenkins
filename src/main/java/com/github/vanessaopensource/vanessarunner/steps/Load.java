package com.github.vanessaopensource.vanessarunner.steps;

import lombok.Getter;
import org.kohsuke.stapler.DataBoundSetter;

public abstract class Load extends VRunner {

    @Getter
    @DataBoundSetter
    String file = "";
}

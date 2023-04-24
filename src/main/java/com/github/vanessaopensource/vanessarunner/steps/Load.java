package com.github.vanessaopensource.vanessarunner.steps;

import org.kohsuke.stapler.DataBoundSetter;

public abstract class Load extends VRunner {

    @DataBoundSetter
    String file = "";
}

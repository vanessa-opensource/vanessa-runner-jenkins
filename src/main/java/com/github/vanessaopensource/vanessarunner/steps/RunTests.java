package com.github.vanessaopensource.vanessarunner.steps;

import org.kohsuke.stapler.DataBoundSetter;

public abstract class RunTests extends VRunner {

    @DataBoundSetter
    String testsPath = "";

    @DataBoundSetter
    String reportAllure = "";

    @DataBoundSetter
    String reportJUnit = "";
}

package com.github.vanessaopensource.vanessarunner.steps;

import lombok.Getter;
import org.kohsuke.stapler.DataBoundSetter;

public abstract class RunTests extends VRunner {

    @Getter
    @DataBoundSetter
    String testsPath = "";

    @Getter
    @DataBoundSetter
    String reportAllure = "";

    @Getter
    @DataBoundSetter
    String reportJUnit = "";
}

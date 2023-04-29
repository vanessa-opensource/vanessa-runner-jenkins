package com.github.vanessaopensource.vanessarunner.steps.core;

import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public abstract class RunTests extends VRunner {

    @DataBoundSetter
    String testsPath = "";

    @DataBoundSetter
    String reportAllure = "";

    @DataBoundSetter
    String reportJUnit = "";
}

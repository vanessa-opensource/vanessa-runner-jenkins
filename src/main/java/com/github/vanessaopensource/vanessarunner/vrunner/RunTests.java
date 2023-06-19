package com.github.vanessaopensource.vanessarunner.vrunner;

import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public abstract class RunTests extends VRunnerInfobase {

    @DataBoundSetter
    private String testsPath = "";

    @DataBoundSetter
    private String reportAllure = "";

    @DataBoundSetter
    private String reportJUnit = "";
}

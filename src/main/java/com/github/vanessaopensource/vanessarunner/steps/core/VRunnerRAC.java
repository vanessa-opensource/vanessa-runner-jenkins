package com.github.vanessaopensource.vanessarunner.steps.core;

import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public abstract class VRunnerRAC extends VRunner {

    @DataBoundSetter
    String rasHost = "localhost";

    @DataBoundSetter
    Integer rasPort = 1545;

    String rasHostPort() {
        return String.format("%s:%d", rasHost, rasPort);
    }
}

package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class UpdateDistStep extends VRunner {

    @Getter
    @DataBoundSetter
    String file = "";

    @Getter
    @DataBoundSetter
    String mergeSettings = "";

    @Getter
    @DataBoundSetter
    Boolean includeObjectsByUnresolvedRefs = false;

    @Getter
    @DataBoundSetter
    Boolean clearUnresolvedRefs = false;

    @Getter
    @DataBoundSetter
    Boolean dumpListOfTwiceChangedProperties = false;

    @Getter
    @DataBoundSetter
    Boolean force = false;

    @DataBoundConstructor
    public UpdateDistStep() {
        super();
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {
        context.setCommand("update");
        context.addParameter(file, "--src");
        context.addParameter(mergeSettings, "--update-settings");
        context.addSwitch(includeObjectsByUnresolvedRefs, "--IncludeObjectsByUnresolvedRefs");
        context.addSwitch(clearUnresolvedRefs, "--ClearUnresolvedRefs");
        context.addSwitch(dumpListOfTwiceChangedProperties, "--DumpListOfTwiceChangedProperties");
        context.addSwitch(force, "--force");

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerUpdateDist";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("UpdateDistStep.DisplayName");
        }
    }
}

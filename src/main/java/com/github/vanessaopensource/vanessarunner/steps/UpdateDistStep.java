package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.Messages;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunner;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerContext;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public class UpdateDistStep extends VRunner {

    @DataBoundSetter
    String file = "";

    @DataBoundSetter
    String mergeSettings = "";

    @DataBoundSetter
    Boolean includeObjectsByUnresolvedRefs = false;

    @DataBoundSetter
    Boolean clearUnresolvedRefs = false;

    @DataBoundSetter
    Boolean dumpListOfTwiceChangedProperties = false;

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

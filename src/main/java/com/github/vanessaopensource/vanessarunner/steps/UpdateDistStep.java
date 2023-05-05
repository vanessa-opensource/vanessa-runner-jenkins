package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.Messages;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunner;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerContext;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerInfobase;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public final class UpdateDistStep extends VRunnerInfobase {

    @DataBoundSetter
    private String file = "";

    @DataBoundSetter
    private String mergeSettings = "";

    @DataBoundSetter
    private Boolean includeObjectsByUnresolvedRefs = false;

    @DataBoundSetter
    private Boolean clearUnresolvedRefs = false;

    @DataBoundSetter
    private Boolean dumpListOfTwiceChangedProperties = false;

    @DataBoundSetter
    private Boolean force = false;

    @DataBoundConstructor
    public UpdateDistStep() {
        super();
    }

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {
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
    public static final class DescriptorImpl extends VRunner.Descriptor {

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

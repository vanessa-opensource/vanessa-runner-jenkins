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
public class InitDevStep extends VRunnerInfobase {

    @DataBoundSetter
    String src = "";

    @DataBoundSetter
    String cf = "";

    @DataBoundSetter
    String dt = "";

    @DataBoundSetter
    Boolean dev = false;

    @DataBoundSetter
    Boolean storage = false;

    @DataBoundSetter
    String storageName = "";

    @DataBoundSetter
    String storageCredentialsID = "";

    @DataBoundSetter
    Integer storageVer = 0;

    @DataBoundSetter
    Boolean v1 = false;

    @DataBoundSetter
    Boolean v2 = false;

    @DataBoundConstructor
    public InitDevStep() {
        super();
    }

    @Override
    public void setCommandContext(VRunnerContext context) throws AbortException {
        context.setCommand("init-dev");
        context.addParameter(src, "--src");
        context.addParameter(cf, "--cf");
        context.addParameter(dt, "--dt");
        context.addSwitch(dev, "--dev");
        context.addSwitch(storage, "--storage");
        context.addParameter(storageName, "--storage-name");
        context.addParameter(storageVer, "--storage-ver");
        context.addSwitch(v1, "--v1");
        context.addSwitch(v2, "--v2");

        context.addCredentialsEnv(storageCredentialsID, VRunner.ENV_STORAGE_USER, VRunner.ENV_STORAGE_PWD);

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerInitDev";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("InitDevStep.DisplayName");
        }
    }
}

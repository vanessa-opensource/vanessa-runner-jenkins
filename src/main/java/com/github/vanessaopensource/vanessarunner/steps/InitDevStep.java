package com.github.vanessaopensource.vanessarunner.steps;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

public class InitDevStep extends VRunner {

    @Getter
    @DataBoundSetter
    String src = "";

    @Getter
    @DataBoundSetter
    String cf = "";

    @Getter
    @DataBoundSetter
    String dt = "";

    @Getter
    @DataBoundSetter
    Boolean dev = false;

    @Getter
    @DataBoundSetter
    Boolean storage = false;

    @Getter
    @DataBoundSetter
    String storageName = "";

    @Getter
    @DataBoundSetter
    String storageCredentialsID = "";

    @Getter
    @DataBoundSetter
    Integer storageVer = 0;

    @Getter
    @DataBoundSetter
    Boolean v1 = false;

    @Getter
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

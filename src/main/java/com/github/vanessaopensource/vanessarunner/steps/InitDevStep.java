package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.vrunner.Messages;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunner;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunnerContext;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunnerInfobase;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public final class InitDevStep extends VRunnerInfobase {

    @DataBoundSetter
    private String src = "";

    @DataBoundSetter
    private String cf = "";

    @DataBoundSetter
    private String dt = "";

    @DataBoundSetter
    private Boolean dev = false;

    @DataBoundSetter
    private Boolean storage = false;

    @DataBoundSetter
    private String storageName = "";

    @DataBoundSetter
    private String storageCredentialsID = "";

    @DataBoundSetter
    private Integer storageVer = 0;

    @DataBoundSetter
    private Boolean v1 = false;

    @DataBoundSetter
    private Boolean v2 = false;

    @DataBoundSetter
    private Boolean ibcmd = false;

    @DataBoundConstructor
    public InitDevStep() {
        super();
    }

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {
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
        context.addSwitch(ibcmd, "--ibcmd");

        context.addCredentialsEnv(storageCredentialsID, VRunner.ENV_STORAGE_USER, VRunner.ENV_STORAGE_PWD);

        super.setCommandContext(context);
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

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

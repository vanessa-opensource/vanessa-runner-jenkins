package com.github.vanessaopensource.vanessarunner.vrunner;

import hudson.AbortException;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.kohsuke.stapler.DataBoundSetter;

@Getter
@Setter
public abstract class VRunnerEDT extends VRunner {

    /**
     * EDT module version
     */
    @DataBoundSetter
    private String version = "";

    /**
     * The location of the workspace for 1C:Enterprise Development Tools execution.
     */
    @DataBoundSetter
    private String workspace = "";

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {
        context.setNonInteractive();
        context.addParameter(version, "--EDTversion");
        addWorkspaceLocation(context);

        super.setCommandContext(context);
    }

    private void addWorkspaceLocation(final VRunnerContext context) throws AbortException {
        if (workspace.isBlank()) {
            val workspaceLocation = context.createTempDir("edt", "workspace");
            context.addParameter(workspaceLocation.getRemote(), "--workspace-location");
        } else {
            context.addParameter(workspace, "--workspace-location");
        }
    }
}

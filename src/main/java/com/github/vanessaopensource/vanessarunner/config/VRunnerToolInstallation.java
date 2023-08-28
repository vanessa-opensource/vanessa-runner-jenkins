package com.github.vanessaopensource.vanessarunner.config;


import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.EnvVars;
import hudson.Extension;
import hudson.model.EnvironmentSpecific;
import hudson.model.Node;
import hudson.model.TaskListener;
import hudson.slaves.NodeSpecific;
import hudson.tools.*;
import hudson.util.FormValidation;
import lombok.val;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Represents vanessa-runner tool installation.
 */
public final class VRunnerToolInstallation extends ToolInstallation implements NodeSpecific<VRunnerToolInstallation>, EnvironmentSpecific<VRunnerToolInstallation> {

    private static final long serialVersionUID = 1L;

    @DataBoundConstructor
    public VRunnerToolInstallation(final String name,
                                   final String home,
                                   final List<? extends ToolProperty<?>> properties) {
        super(name, home, properties);
    }

    @NonNull
    public VRunnerToolInstallation forNode(final Node node, final TaskListener log) throws IOException, InterruptedException {
        val nodeHome = translateFor(node, log);
        return new VRunnerToolInstallation(getName(), nodeHome, getProperties().toList());
    }

    @NonNull
    public VRunnerToolInstallation forEnvironment(final EnvVars environment) {
        val environmentHome = environment.expand(getHome());
        return new VRunnerToolInstallation(getName(), environmentHome, getProperties().toList());
    }

    @Extension
    public static final class DescriptorImpl extends ToolDescriptor<VRunnerToolInstallation> {

        public DescriptorImpl() {
            super();
            load();
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return "Vanessa-runner";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject json) {
            val installations = req.bindJSONToList(VRunnerToolInstallation.class, json.get("tool"));
            setInstallations(installations.toArray(new VRunnerToolInstallation[0]));
            save();
            return true;
        }

        @NonNull
        @Override
        public List<? extends ToolInstaller> getDefaultInstallers() {
            return List.of(VRunnerInstaller.newDefaultInstallation());
        }

        @Override
        public FormValidation doCheckHome(@QueryParameter final File value) {
            return FormValidation.ok();
        }
    }
}

package com.github.vanessaopensource.vanessarunner.vrunner;

import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials;
import com.github.vanessaopensource.vanessarunner.config.VRunnerToolInstallation;
import com.google.common.collect.ImmutableSet;
import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.*;
import hudson.model.Node;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.slaves.WorkspaceList;
import hudson.util.ArgumentListBuilder;
import hudson.util.Secret;
import jenkins.model.Jenkins;
import lombok.val;
import org.jenkinsci.plugins.workflow.steps.StepContext;

import java.io.IOException;
import java.util.*;

public final class VRunnerContext {

    private final ArgumentListBuilder args = new ArgumentListBuilder();

    private final Map<Integer, Result> exitCodes = new HashMap<>();

    private final Launcher launcher;
    private final EnvVars env;
    private final FilePath workSpace;
    private final TaskListener listener;
    private final Run<?, ?> run;
    private final Node node;
    private final FilePath tempDir;

    private Boolean isInteractive = true;

    @NonNull
    public static Set<? extends Class<?>> getRequiredContext() {
        return ImmutableSet.of(Launcher.class, EnvVars.class, FilePath.class,
                TaskListener.class, Run.class);
    }

    public VRunnerContext(final StepContext stepContext) throws IOException, InterruptedException {

        launcher = Objects.requireNonNull(stepContext.get(Launcher.class));
        env = Objects.requireNonNull(stepContext.get(EnvVars.class));
        workSpace = Objects.requireNonNull(stepContext.get(FilePath.class));
        listener = Objects.requireNonNull(stepContext.get(TaskListener.class));
        run = Objects.requireNonNull(stepContext.get(Run.class));
        node = Objects.requireNonNull(stepContext.get(Node.class));

        val workSpaceTmp = WorkspaceList.tempDir(workSpace);
        assert workSpaceTmp != null;
        workSpace.mkdirs();
        workSpaceTmp.mkdirs();
        tempDir = workSpaceTmp.createTempDir("vrunner", "tmp");
    }

    public void setCommand(final String command) {
        args.add(command);
    }

    public void addSwitch(final Boolean switchId, final String key) {
        if (switchId) {
            args.add(key);
        }
    }

    public void addSwitch(final Boolean switchId, final String key, final String value) {
        if (switchId) {
            args.add(key).add(value);
        }
    }

    public void addParameter(final String value, final String key) {
        if (!value.isBlank()) {
            args.add(key).add(value);
        }

    }

    public void addParameter(final Integer value, final String key) {
        if (value != 0) {
            args.add(key).add(value.toString());
        }
    }

    public void addCredentialsEnv(final String id, final String usernameEnv, final String passwordEnv) throws AbortException {
        if (id.isBlank()) {
            return;
        }

        val credentials = CredentialsProvider.findCredentialById(id,
                StandardUsernamePasswordCredentials.class,
                run);
        if (credentials == null) {
            val message = String.format(Messages.getString("VRunnerExecution.CredentialsNotFound"), id);
            throw new AbortException(String.format(message, id));
        }

        val username = credentials.getUsername();
        if (username.isBlank()) {
            return;
        }
        val password = Secret.toString(credentials.getPassword());
        env.put(usernameEnv, username);
        env.put(passwordEnv, password);
    }

    public void setEnvVar(final String key, final String value) {
        env.put(key, value);
    }

    public Integer getBuildNumber() {
        return run.number;
    }

    public void putExitCodeResult(final Integer exitCode, final Result result) {
        exitCodes.put(exitCode, result);
    }

    public void verifyExitCode(final Integer exitCode) throws AbortException {
        if (exitCodes.containsKey(exitCode)) {
            val result = exitCodes.get(exitCode);
            run.setResult(result);
        } else {
            throw new AbortException(String.format(Messages.getString("VRunnerExecution.RunAborted"), exitCode));
        }
    }

    public void setNonInteractive() {
        isInteractive = false;
    }

    public FilePath createTempFile(final String prefix, final String suffix) throws AbortException {
        try {
            return tempDir.createTempFile(prefix, suffix);
        } catch (Exception ex) {
            throw new AbortException(ex.getLocalizedMessage());
        }
    }

    public FilePath createTempDir(final String prefix, final String suffix) throws AbortException {
        try {
            return tempDir.createTempDir(prefix, suffix);
        } catch (Exception ex) {
            throw new AbortException(ex.getLocalizedMessage());
        }
    }

    public FilePath workspaceChild(final String child) {
        return workSpace.child(child);
    }

    public void cleanup() throws IOException, InterruptedException {
        tempDir.deleteRecursive();
    }

    public Launcher.ProcStarter createStarter() throws IOException, InterruptedException {

        val launcherArgs = launcherArgs();
        launcherArgs.add(args.toList());

        return launcher.launch().cmds(launcherArgs)
                .quiet(true)
                .pwd(workSpace)
                .envs(env)
                .stdout(listener)
                .stderr(listener.getLogger());
    }

    private String getVRunnerLocation(final VRunnerToolInstallation installation) throws IOException, InterruptedException {
        val tool = installation.translate(node, env, listener);
        return tool.getHome() + "/vrunner";
    }

    @CheckForNull
    private VRunnerToolInstallation getInstallation() {

        val jenkinsInstance = Jenkins.get();
        val descriptor = (VRunnerToolInstallation.DescriptorImpl) Objects.requireNonNull(
                jenkinsInstance.getDescriptor(VRunnerToolInstallation.class));

        val installations = descriptor.getInstallations();
        if (installations != null && installations.length > 0) {
            return installations[0];
        } else {
            return null;
        }
    }

    @NonNull
    private ArgumentListBuilder launcherArgs() throws IOException, InterruptedException {

        val installation = getInstallation();

        val launcherArgs = new ArgumentListBuilder();
        if (launcher.isUnix()) {
            if (isInteractive) {
                launcherArgs.addTokenized("xvfb-run -a");
            }

            if (installation == null) {
                launcherArgs.addTokenized("vrunner");
            } else {
                launcherArgs.addQuoted(getVRunnerLocation(installation));
            }
        } else {
            launcherArgs.add("cmd.exe");
            launcherArgs.add("/C");

            if (installation == null) {
                launcherArgs.addTokenized("chcp 65001 && vrunner");
            } else {
                launcherArgs.add(getVRunnerLocation(installation));
            }
        }

        return launcherArgs;
    }
}

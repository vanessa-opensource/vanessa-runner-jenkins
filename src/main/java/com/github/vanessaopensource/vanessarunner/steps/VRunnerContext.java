package com.github.vanessaopensource.vanessarunner.steps;

import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials;
import com.google.common.collect.ImmutableSet;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.slaves.WorkspaceList;
import hudson.util.ArgumentListBuilder;
import hudson.util.Secret;
import org.jenkinsci.plugins.workflow.steps.StepContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class VRunnerContext {

    private final ArgumentListBuilder args = new ArgumentListBuilder();

    private final Map<Integer, Result> exitCodes = new HashMap<>();

    private final Launcher launcher;
    private final EnvVars env;
    private final FilePath workSpace;
    private final TaskListener listener;
    private final Run<?, ?> run;
    private final FilePath tempDir;

    @NonNull
    public static Set<? extends Class<?>> getRequiredContext() {
        return ImmutableSet.of(Launcher.class, EnvVars.class, FilePath.class,
                TaskListener.class, Run.class);
    }

    public VRunnerContext(StepContext stepContext) throws IOException, InterruptedException {

        launcher = Objects.requireNonNull(stepContext.get(Launcher.class));
        env = Objects.requireNonNull(stepContext.get(EnvVars.class));
        workSpace = Objects.requireNonNull(stepContext.get(FilePath.class));
        listener = Objects.requireNonNull(stepContext.get(TaskListener.class));
        run = Objects.requireNonNull(stepContext.get(Run.class));

        var workSpaceTmp = WorkspaceList.tempDir(workSpace);
        assert workSpaceTmp != null;
        workSpace.mkdirs();
        workSpaceTmp.mkdirs();
        tempDir = workSpaceTmp.createTempDir("vrunner", "tmp");
    }

    public void setCommand(String command) {
        args.add(command);
    }

    public void addSwitch(Boolean switchId, String key) {
        if (switchId) {
            args.add(key);
        }
    }

    public void addSwitch(Boolean switchId, String key, String value) {
        if (switchId) {
            args.add(key).add(value);
        }
    }

    public void addParameter(String value, String key) {
        if (!value.isBlank()) {
            args.add(key).add(value);
        }

    }

    public void addParameter(Integer value, String key) {
        if (value != 0) {
            args.add(key).add(value.toString());
        }
    }

    public void addCredentialsEnv(String id, String usernameEnv, String passwordEnv) throws AbortException {
        if (id.isBlank()) {
            return;
        }

        var credentials = CredentialsProvider.findCredentialById(id,
                StandardUsernamePasswordCredentials.class,
                run);
        if (credentials == null) {
            var message = String.format(Messages.getString("VRunnerExecution.CredentialsNotFound"), id);
            throw new AbortException(String.format(message, id));
        }

        var username = credentials.getUsername();
        if(username.isBlank()) {
            return;
        }
        var password = Secret.toString(credentials.getPassword());
        env.put(usernameEnv, username);
        env.put(passwordEnv, password);
    }

    public void setEnvVar(String key, String value) {
        env.put(key, value);
    }

    public Integer getBuildNumber() {
        return run.number;
    }

    public void putExitCodeResult(Integer exitCode, Result result) {
        exitCodes.put(exitCode, result);
    }

    public void verifyExitCode(Integer exitCode) throws AbortException {
        if (exitCodes.containsKey(exitCode)) {
            var result = exitCodes.get(exitCode);
            run.setResult(result);
        } else {
            throw new AbortException(String.format(Messages.getString("VRunnerExecution.RunAborted"), exitCode));
        }
    }

    public FilePath createTempFile(final String prefix, final String suffix) throws IOException, InterruptedException {
        return tempDir.createTempFile(prefix, suffix);
    }

    public void cleanup() throws IOException, InterruptedException {
        tempDir.deleteRecursive();
    }

    public Launcher.ProcStarter createStarter() {

        var launcherArgs = launcherArgs();
        launcherArgs.add(args.toList());

        return launcher.launch().cmds(launcherArgs)
                .pwd(workSpace)
                .envs(env)
                .stdout(listener)
                .stderr(listener.getLogger());
    }

    @NonNull
    private ArgumentListBuilder launcherArgs() {

        var launcherArgs = new ArgumentListBuilder();
        if (launcher.isUnix()) {
            launcherArgs.add("vrunner");
        } else {
            launcherArgs.add("cmd.exe");
            launcherArgs.add("/C");
            launcherArgs.add("chcp");
            launcherArgs.add("65001");
            launcherArgs.add("&&");
            launcherArgs.add("vrunner");
        }

        return launcherArgs;
    }
}

package com.github.vanessaopensource.vanessarunner.steps;

import com.cloudbees.plugins.credentials.Credentials;
import com.cloudbees.plugins.credentials.CredentialsScope;
import com.cloudbees.plugins.credentials.SystemCredentialsProvider;
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunner;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.FilePath;
import hudson.slaves.WorkspaceList;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.jvnet.hudson.test.JenkinsRule;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

@Extension
public class VRunnerRule {

    public static final String CREDS_ADMINISTRATOR_EMPTY = "vrunnerAdministratorEmpty";

    private JenkinsRule j;

    private Jenkins jenkins;

    @NonNull
    public static VRunnerRule createRule(JenkinsRule j) {
        var rule = new VRunnerRule();
        rule.j = j;
        rule.jenkins = j.getInstance();
        return rule;
    }

    @NonNull
    public WorkflowJob createWorkFlowJob(String script) throws IOException {

        var job = j.createProject(WorkflowJob.class);
        var definition = new CpsFlowDefinition(script, false);
        job.setDefinition(definition);

        return job;
    }

    @NonNull
    public WorkflowJob createWorkFlowJob(VRunner step) throws IOException {
        var script = new CommandBuilder(step).buildScript();
        return createWorkFlowJob(script);
    }

    @NonNull
    public FilePath createWorkSpace(WorkflowJob job) throws IOException, InterruptedException {

        var workspace = jenkins.getWorkspaceFor(job);
        assert workspace != null;
        workspace.mkdirs();

        var tempDir = WorkspaceList.tempDir(workspace);
        assert tempDir != null;
        tempDir.mkdirs();

        return workspace;
    }

    @NonNull
    public static WorkflowRun runJob(WorkflowJob job) throws ExecutionException, InterruptedException {

        var future = job.scheduleBuild2(0);
        assert future != null;
        return future.get();
    }

    public static void assertChildFileExists(String child, FilePath filePath) throws IOException, InterruptedException {
        assert filePath.child(child).exists();
    }

    public static void provideCredentialsAdministratorEmpty() {

        var username = "Administrator";
        var password = "";
        var credentials = new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL,
                CREDS_ADMINISTRATOR_EMPTY, CREDS_ADMINISTRATOR_EMPTY,
                username, password);

        addCredentials(credentials);
    }

    private static void addCredentials(Credentials credentials) {
        var credentialsProvider = SystemCredentialsProvider.getInstance().getCredentials();
        credentialsProvider.add(credentials);
    }

    public static void createLocalData(Class<?> clazz, FilePath workspace) throws Exception {
        createLocalData(clazz, "", workspace);
    }

    public static void createLocalData(Class<?> clazz, String alterName, FilePath workspace) throws Exception {
        var res = findDataResource(clazz);
        var source = new File(res.toURI());
        new FilePath(source).unzip(workspace);
    }

    private static URL findDataResource(Class<?> clazz) {
        final var suffixes = new String[]{"/", ".zip"};

        for (String suffix : suffixes) {
            URL res = clazz.getResource(clazz.getSimpleName() + suffix);
            if (res != null) return res;
        }

        throw new AssertionError("No test resource was found");
    }
}

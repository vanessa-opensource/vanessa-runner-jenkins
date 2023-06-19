package com.github.vanessaopensource.vanessarunner.steps;

import com.cloudbees.plugins.credentials.Credentials;
import com.cloudbees.plugins.credentials.CredentialsScope;
import com.cloudbees.plugins.credentials.SystemCredentialsProvider;
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl;
import com.github.vanessaopensource.vanessarunner.vrunner.VRunner;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.FilePath;
import hudson.slaves.WorkspaceList;
import jenkins.model.Jenkins;
import lombok.val;
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.jvnet.hudson.test.JenkinsRule;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private WorkflowJob createWorkFlowJob(String script) throws IOException {

        var job = j.createProject(WorkflowJob.class);
        var definition = new CpsFlowDefinition(script, false);
        job.setDefinition(definition);

        return job;
    }

    @NonNull
    private WorkflowJob createWorkFlowJob(VRunner step) throws IOException {
        var script = new CommandBuilder(step).buildScript();
        return createWorkFlowJob(script);
    }

    @NonNull
    private FilePath createWorkSpace(WorkflowJob job) throws IOException, InterruptedException {

        val workspace = getWorkspaceFor(job);
        workspace.mkdirs();

        var tempDir = WorkspaceList.tempDir(workspace);
        assert tempDir != null;
        tempDir.mkdirs();

        return workspace;
    }

    @NonNull
    private static WorkflowRun runJob(WorkflowJob job) throws ExecutionException, InterruptedException {

        var future = job.scheduleBuild2(0);
        assert future != null;
        return future.get();
    }

    @NonNull
    public WorkflowRun runStep(VRunner step) throws ExecutionException, InterruptedException, IOException {
        val job = createWorkFlowJob(step);
        return runJob(job);
    }

    @NonNull
    public WorkflowRun runStep(VRunner step, Class<?> clazz) throws Exception {
        val job = createWorkFlowJob(step);
        val workSpace = createWorkSpace(job);
        createLocalData(clazz, workSpace);
        return runJob(job);
    }

    public void assertChildFileExists(String child, WorkflowRun run) throws IOException, InterruptedException {

        val job = run.getParent();
        val workSpace = getWorkspaceFor(job);

        assertTrue(workSpace.child(child).exists());
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

    private static void createLocalData(Class<?> clazz, FilePath workspace) throws Exception {
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

    private FilePath getWorkspaceFor(final WorkflowJob job) {
        return Objects.requireNonNull(jenkins.getWorkspaceFor(job));
    }
}

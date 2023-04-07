package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class RunStepTest {

    @Test
    public void runCloseEnterprise(JenkinsRule j) throws Exception {

        var r = VRunnerRule.createRule(j);

        // given
        var command = String.format("vrunnerRun execute:'%s', ibConnection: '%s', language: '%s'",
                "$runnerRoot/epf/ЗакрытьПредприятие.epf", "/Fbuild/ib", "en");
        var script = VRunnerRule.buildScript(command);
        var job = r.createWorkFlowJob(script);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(RunStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("ИНФОРМАЦИЯ - Выполнение команды/действия в режиме 1С:Предприятие завершено", run);
    }

    @Test
    public void runInvalidUser(JenkinsRule j) throws Exception {

        var r = VRunnerRule.createRule(j);

        // given
        VRunnerRule.provideCredentialsAdministratorEmpty();
        var command = String.format("vrunnerRun execute:'%s', ibConnection: '%s', " +
                        "databaseCredentialsID:'%s',language: '%s'",
                "$runnerRoot/epf/ЗакрытьПредприятие.epf", "/Fbuild/ib",
                VRunnerRule.CREDS_ADMINISTRATOR_EMPTY, "en");

        var script = VRunnerRule.buildScript(command);
        var job = r.createWorkFlowJob(script);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(RunStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Infobase user not authenticated", run);
    }
}
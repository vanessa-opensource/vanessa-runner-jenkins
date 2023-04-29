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
        var step = new RunStep();
        step.setExecute("$runnerRoot/epf/ЗакрытьПредприятие.epf");
        step.setIbConnection("/Fbuild/ib");
        step.setLanguage("en");

        var job = r.createWorkFlowJob(step);
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
        var step = new RunStep();
        step.setExecute("$runnerRoot/epf/ЗакрытьПредприятие.epf");
        step.setDatabaseCredentialsID(VRunnerRule.CREDS_ADMINISTRATOR_EMPTY);
        step.setIbConnection("/Fbuild/ib");
        step.setLanguage("en");

        var job = r.createWorkFlowJob(step);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.provideCredentialsAdministratorEmpty();
        VRunnerRule.createLocalData(RunStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Infobase user not authenticated", run);
    }
}
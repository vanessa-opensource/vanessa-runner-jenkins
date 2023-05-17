package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class RunStepTest {

    @Test
    public void runCloseEnterprise(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new RunStep();
        step.setExecute("$runnerRoot/epf/ЗакрытьПредприятие.epf");
        step.setIbPath("build/ib");
        step.setLanguage("en");

        // when
        val run = r.runStep(step, RunStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("ИНФОРМАЦИЯ - Выполнение команды/действия в режиме 1С:Предприятие завершено", run);
    }

    @Test
    public void runInvalidUser(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new RunStep();
        step.setExecute("$runnerRoot/epf/ЗакрытьПредприятие.epf");
        step.setDatabaseCredentialsID(VRunnerRule.CREDS_ADMINISTRATOR_EMPTY);
        step.setIbConnection("/Fbuild/ib");
        step.setLanguage("en");

        VRunnerRule.provideCredentialsAdministratorEmpty();

        // when
        val run = r.runStep(step, RunStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Infobase user not authenticated", run);
    }
}
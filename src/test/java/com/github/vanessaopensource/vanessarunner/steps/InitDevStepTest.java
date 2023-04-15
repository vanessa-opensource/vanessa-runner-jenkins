package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins()
public class InitDevStepTest {

    @Test
    public void initSrc(JenkinsRule j) throws Exception {
        var r = VRunnerRule.createRule(j);

        // given
        var step = new InitDevStep();
        step.src = "src/cf";
        step.noCacheUse = true;
        step.language = "en";

        var job = r.createWorkFlowJob(step);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(InitDevStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Загрузка конфигурации из файлов успешно завершена", run);
        j.assertLogContains("Обновление конфигурации БД завершено", run);
        j.assertLogContains("Database configuration successfully updated", run);
        VRunnerRule.assertChildFileExists("build/ib", workSpace);
    }

    @Test
    public void initSrcDev(JenkinsRule j) throws Exception {
        var r = VRunnerRule.createRule(j);

        // given
        var step = new InitDevStep();
        step.src = "src/cf";
        step.dev = true;
        step.noCacheUse = true;
        step.language = "en";

        var job = r.createWorkFlowJob(step);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(InitDevStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Загрузка конфигурации из файлов успешно завершена", run);
        j.assertLogNotContains("Запускаю обновление конфигурации БД", run);
        VRunnerRule.assertChildFileExists("build/ibservice/1Cv8.1CD", workSpace);
    }

    @Test
    public void initSrcIbConnection(JenkinsRule j) throws Exception {
        var r = VRunnerRule.createRule(j);

        // given
        var step = new InitDevStep();
        step.src = "src/cf";
        step.ibConnection = "/Fbuild/base1";
        step.noCacheUse = true;
        step.language = "en";

        var job = r.createWorkFlowJob(step);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(InitDevStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Загрузка конфигурации из файлов успешно завершена", run);
        j.assertLogContains("Обновление конфигурации БД завершено", run);
        j.assertLogContains("Database configuration successfully updated", run);
        VRunnerRule.assertChildFileExists("build/base1", workSpace);
    }

    @Test
    public void initDt(JenkinsRule j) throws Exception {
        var r = VRunnerRule.createRule(j);

        // given
        var step = new InitDevStep();
        step.dt = "bin/1Cv8.dt";
        step.noCacheUse = true;
        step.language = "en";

        var job = r.createWorkFlowJob(step);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(InitDevStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Загружаем dt", run);
        j.assertLogContains("Обновление конфигурации БД завершено", run);
        VRunnerRule.assertChildFileExists("build/ib", workSpace);
    }

    @Test
    public void initStorage(JenkinsRule j) throws Exception {
        var r = VRunnerRule.createRule(j);

        // given
        var step = new InitDevStep();
        step.storage = true;
        step.storageName = "bin/storage";
        step.storageCredentialsID = VRunnerRule.CREDS_ADMINISTRATOR_EMPTY;
        step.noCacheUse = true;
        step.language = "en";

        var job = r.createWorkFlowJob(step);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.provideCredentialsAdministratorEmpty();
        VRunnerRule.createLocalData(InitDevStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Обновление конфигурации из хранилища завершено", run);
        j.assertLogContains("Обновление конфигурации БД завершено", run);
        VRunnerRule.assertChildFileExists("build/ib", workSpace);
    }
}

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
        var command = String.format("vrunnerInitDev src:'%s', noCacheUse:%b, language:'%s'",
                "src/cf", true, "en");
        var script = VRunnerRule.buildScript(command);
        var job = r.createWorkFlowJob(script);
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
        var command = String.format("vrunnerInitDev src:'%s', dev:%b, noCacheUse:%b, language:'%s'",
                "src/cf", true, true, "en");
        var script = VRunnerRule.buildScript(command);
        var job = r.createWorkFlowJob(script);
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
        var command = String.format("vrunnerInitDev src:'%s', ibConnection:'%s', noCacheUse:%b, language:'%s'",
                "src/cf", "/Fbuild/base1", true, "en");
        var script = VRunnerRule.buildScript(command);
        var job = r.createWorkFlowJob(script);
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
        var command = String.format("vrunnerInitDev dt:'%s', noCacheUse:%b, language:'%s'",
                "bin/1Cv8.dt", true, "en");
        var script = VRunnerRule.buildScript(command);
        var job = r.createWorkFlowJob(script);
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
        VRunnerRule.provideCredentialsAdministratorEmpty();
        var command = String.format("vrunnerInitDev storage:%b, storageName:'%s', storageCredentialsID:'%s', " +
                        "noCacheUse:%b, language:'%s'",
                true, "bin/storage", VRunnerRule.CREDS_ADMINISTRATOR_EMPTY,
                true, "en");
        var script = VRunnerRule.buildScript(command);
        var job = r.createWorkFlowJob(script);
        var workSpace = r.createWorkSpace(job);
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

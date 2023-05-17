package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins()
public class InitDevStepTest {

    @Test
    public void initSrc(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new InitDevStep();
        step.setSrc("src/cf");
        step.setNoCacheUse(true);
        step.setLanguage("en");

        // when
        val run = r.runStep(step, InitDevStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Загрузка конфигурации из файлов успешно завершена", run);
        j.assertLogContains("Обновление конфигурации БД завершено", run);
        j.assertLogContains("Database configuration successfully updated", run);
        r.assertChildFileExists("build/ib", run);
    }

    @Test
    public void initSrcDev(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new InitDevStep();
        step.setSrc("src/cf");
        step.setDev(true);
        step.setNoCacheUse(true);
        step.setLanguage("en");

        // when
        val run = r.runStep(step, InitDevStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Загрузка конфигурации из файлов успешно завершена", run);
        j.assertLogNotContains("Запускаю обновление конфигурации БД", run);
        r.assertChildFileExists("build/ibservice/1Cv8.1CD", run);
    }

    @Test
    public void initSrcIbConnection(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new InitDevStep();
        step.setSrc("src/cf");
        step.setIbConnection("/Fbuild/base1");
        step.setNoCacheUse(true);
        step.setLanguage("en");

        // when
        val run = r.runStep(step, InitDevStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Загрузка конфигурации из файлов успешно завершена", run);
        j.assertLogContains("Обновление конфигурации БД завершено", run);
        j.assertLogContains("Database configuration successfully updated", run);
        r.assertChildFileExists("build/base1", run);
    }

    @Test
    public void initDt(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new InitDevStep();
        step.setDt("bin/1Cv8.dt");
        step.setNoCacheUse(true);
        step.setLanguage("en");

        // when
        val run = r.runStep(step, InitDevStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Загружаем dt", run);
        j.assertLogContains("Обновление конфигурации БД завершено", run);
        r.assertChildFileExists("build/ib", run);
    }

    @Test
    public void initStorage(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new InitDevStep();
        step.setStorage(true);
        step.setStorageName("bin/storage");
        step.setStorageCredentialsID(VRunnerRule.CREDS_ADMINISTRATOR_EMPTY);
        step.setNoCacheUse(true);
        step.setLanguage("en");

        VRunnerRule.provideCredentialsAdministratorEmpty();

        // when
        val run = r.runStep(step, InitDevStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Обновление конфигурации из хранилища завершено", run);
        j.assertLogContains("Обновление конфигурации БД завершено", run);
        r.assertChildFileExists("build/ib", run);
    }
}

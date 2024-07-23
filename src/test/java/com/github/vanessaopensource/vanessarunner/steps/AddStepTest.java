package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
class AddStepTest {

    @Test
    void runTestsOK(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new AddStep();
        step.setTestsPath("features/addOK.feature");
        step.setVanessasettings("features/vb-conf.json");
        step.setReportAllure("build/allure");
        step.setIbConnection("/Fbuild/ib");
        step.setLanguage("en");

        // when
        val run = r.runStep(step, AddStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("ИНФОРМАЦИЯ - Все фичи/сценарии выполнены!", run);
        r.assertChildFileExists("build/allure", run);
    }

    @Test
    void runTestsFail(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new AddStep();
        step.setTestsPath("features/addFail.feature");
        step.setVanessasettings("features/vb-conf.json");
        step.setReportJUnit("build/junit");
        step.setIbConnection("/Fbuild/ib");
        step.setLanguage("en");

        // when
        val run = r.runStep(step, AddStepTest.class);

        // then
        j.assertBuildStatus(Result.UNSTABLE, run);
        j.assertLogContains("ОШИБКА - Часть фич/сценариев упала!", run);
        r.assertChildFileExists("build/junit", run);
    }
}

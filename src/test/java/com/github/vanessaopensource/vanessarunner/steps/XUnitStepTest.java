package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class XUnitStepTest {

    @Test
    public void runConfigTestsOK(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new XUnitStep();
        step.setTestsPath("testExtensionOK");
        step.setConfigTests(true);
        step.setIbConnection("/Fbuild/ib");
        step.setReportJUnit("build/junit/xunit.xml");
        step.setLanguage("en");

        // when
        val run = r.runStep(step, XUnitStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("ИНФОРМАЦИЯ - Все тесты выполнены!", run);
        r.assertChildFileExists("build/junit", run);
    }

    @Test
    public void runConfigTestsFail(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new XUnitStep();
        step.setTestsPath("testExtensionFail");
        step.setConfigTests(true);
        step.setIbConnection("/Fbuild/ib");
        step.setLanguage("en");

        // when
        val run = r.runStep(step, XUnitStepTest.class);

        // then
        j.assertBuildStatus(Result.UNSTABLE, run);
        j.assertLogContains("ОШИБКА - Часть тестов упала!", run);
    }

    @Test
    public void runSmokeTests(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new XUnitStep();
        step.setTestsPath("$addroot/tests/smoke");
        step.setIbConnection("/Fbuild/ib");
        step.setReportAllure("build/allure/xunit.json");
        step.setLanguage("en");

        // when
        val run = r.runStep(step, XUnitStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("ИНФОРМАЦИЯ - Все тесты выполнены!", run);
        r.assertChildFileExists("build/allure", run);
    }
}

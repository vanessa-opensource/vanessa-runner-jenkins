package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class XUnitStepTest {

    @Test
    public void runConfigTestsOK(JenkinsRule j) throws Exception {
        var r = VRunnerRule.createRule(j);

        // given
        var step = new XUnitStep();
        step.setTestsPath("testExtensionOK");
        step.setConfigTests(true);
        step.setIbConnection("/Fbuild/ib");
        step.setReportJUnit("build/junit/xunit.xml");
        step.setLanguage("en");

        var job = r.createWorkFlowJob(step);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(XUnitStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("ИНФОРМАЦИЯ - Все тесты выполнены!", run);
        VRunnerRule.assertChildFileExists("build/junit", workSpace);
    }

    @Test
    public void runConfigTestsFail(JenkinsRule j) throws Exception {
        var r = VRunnerRule.createRule(j);

        // given
        var step = new XUnitStep();
        step.setTestsPath("testExtensionFail");
        step.setConfigTests(true);
        step.setIbConnection("/Fbuild/ib");
        step.setLanguage("en");

        var job = r.createWorkFlowJob(step);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(XUnitStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.UNSTABLE, run);
        j.assertLogContains("ОШИБКА - Часть тестов упала!", run);
    }

    @Test
    public void runSmokeTests(JenkinsRule j) throws Exception {
        var r = VRunnerRule.createRule(j);

        // given
        var step = new XUnitStep();
        step.setTestsPath("$addroot/tests/smoke");
        step.setIbConnection("/Fbuild/ib");
        step.setReportAllure("build/allure/xunit.json");
        step.setLanguage("en");

        var job = r.createWorkFlowJob(step);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(XUnitStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("ИНФОРМАЦИЯ - Все тесты выполнены!", run);
        VRunnerRule.assertChildFileExists("build/allure", workSpace);
    }
}

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
        step.testsPath = "testExtensionOK";
        step.configTests = true;
        step.ibConnection = "/Fbuild/ib";
        step.reportJUnit = "build/junit/xunit.xml";
        step.language = "en";

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
        step.testsPath = "testExtensionFail";
        step.configTests = true;
        step.ibConnection = "/Fbuild/ib";
        step.language = "en";

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
        step.testsPath = "$addroot/tests/smoke";
        step.ibConnection = "/Fbuild/ib";
        step.reportAllure = "build/allure/xunit.json";
        step.language = "en";

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

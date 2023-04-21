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
        step.testsPath = "features/addOK.feature";
        step.reportAllure = "build/allure";
        step.ibConnection = "/Fbuild/ib";
        step.language = "en";

        var job = r.createWorkFlowJob(step);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(AddStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("ИНФОРМАЦИЯ - Все фичи/сценарии выполнены!", run);
        VRunnerRule.assertChildFileExists("build/allure", workSpace);
    }

    @Test
    void runTestsFail(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new AddStep();
        step.testsPath = "features/addFail.feature";
        step.reportJUnit = "build/junit";
        step.ibConnection = "/Fbuild/ib";
        step.language = "en";

        var job = r.createWorkFlowJob(step);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(AddStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.UNSTABLE, run);
        j.assertLogContains("ОШИБКА - Часть фич/сценариев упала!", run);
        VRunnerRule.assertChildFileExists("build/junit", workSpace);
    }
}

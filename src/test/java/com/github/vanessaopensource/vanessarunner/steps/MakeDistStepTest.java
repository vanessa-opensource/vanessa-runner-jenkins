package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class MakeDistStepTest {
    @Test
    void initSrc(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new MakeDistStep();
        step.setSrc("src/cf");
        step.setOut("1cv8_$version.cf");
        step.setBuildNumber(9999);
        step.setLanguage("en");

        val job = r.createWorkFlowJob(step);
        val workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(InitDevStepTest.class, workSpace);

        // when
        val run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Distribution files successfully created", run);
        VRunnerRule.assertChildFileExists("1cv8_1.0.0.9999.cf", workSpace);
    }

    @Test
    void initSrcWithBuldNumber(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new MakeDistStep();
        step.setSrc("src/cf");
        step.setOut("1cv8_$version.cf");
        step.setWithBuildNumber(true);
        step.setLanguage("en");

        val job = r.createWorkFlowJob(step);
        val workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(InitDevStepTest.class, workSpace);

        // when
        val run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Distribution files successfully created", run);
        VRunnerRule.assertChildFileExists("1cv8_1.0.0.1.cf", workSpace);
    }
}

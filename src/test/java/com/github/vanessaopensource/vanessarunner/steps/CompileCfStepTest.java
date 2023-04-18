package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class CompileCfStepTest {

    @Test
    void initSrc(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new CompileCfStep();
        step.src = "src/cf";
        step.out = "1cv8.cf";
        step.buildNumber = 9999;
        step.language = "en";

        val job = r.createWorkFlowJob(step);
        val workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(InitDevStepTest.class, workSpace);

        // when
        val run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Выгрузка в файл завершена.", run);
        j.assertLogContains("Configuration successfully saved", run);
        VRunnerRule.assertChildFileExists("1cv8.cf", workSpace);
        VRunnerRule.assertBuildNumber("src/cf/Configuration.xml", 9999, workSpace);
    }
}

package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins()
public class CompileCfeStepTest {

    @Test
    public void initSrc(JenkinsRule j) throws Exception {
        var r = VRunnerRule.createRule(j);

        // given
        var step = new CompileCfeStep();
        step.setSrc("src/cfe");
        step.setOut("1cv8_$version.cfe");
        step.setBuildNumber(9999);
        step.setLanguage("en");

        var job = r.createWorkFlowJob(step);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(CompileCfeStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Выгрузка в файл завершена.", run);
        j.assertLogContains("Configuration successfully saved", run);
        VRunnerRule.assertChildFileExists("1cv8_1.1.0.9999.cfe", workSpace);
    }

    @Test
    public void initSrcWithBuldNumber(JenkinsRule j) throws Exception {
        var r = VRunnerRule.createRule(j);

        // given
        var step = new CompileCfeStep();
        step.setSrc("src/cfe");
        step.setOut("1cv8_$version.cfe");
        step.setWithBuildNumber(true);
        step.setLanguage("en");

        var job = r.createWorkFlowJob(step);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(CompileCfeStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Выгрузка в файл завершена.", run);
        j.assertLogContains("Configuration successfully saved", run);
        VRunnerRule.assertChildFileExists("1cv8_1.1.0.1.cfe", workSpace);
    }
}

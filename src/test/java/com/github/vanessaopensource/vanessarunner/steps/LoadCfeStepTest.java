package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class LoadCfeStepTest {

    @Test
    public void loadCf(JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new LoadCfeStep();
        step.file = "bin/1cv8.cfe";
        step.extension = "Extension1";
        step.ibConnection = "/Fbuild/ib";
        step.language = "en";

        val job = r.createWorkFlowJob(step);
        val workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(LoadCfStepTest.class, workSpace);

        // when
        val run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("ИНФОРМАЦИЯ - Загрузка расширения из cfe-файла успешно завершена!", run);
    }
}

package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class LoadCfStepTest {

    @Test
    public void loadCf(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new LoadCfStep();
        step.setFile("bin/1cv8.cf");
        step.setIbConnection("/Fbuild/ib");
        step.setLanguage("en");

        // when
        val run = r.runStep(step, LoadCfStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("ИНФОРМАЦИЯ - Загрузка конфигурации из файла cf успешно завершена!", run);
    }
}

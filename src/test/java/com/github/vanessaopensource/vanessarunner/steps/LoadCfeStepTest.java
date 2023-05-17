package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class LoadCfeStepTest {

    @Test
    public void loadCfe(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new LoadCfeStep();
        step.setFile("bin/1cv8.cfe");
        step.setExtension("Extension1");
        step.setIbConnection("/Fbuild/ib");
        step.setLanguage("en");

        // when
        val run = r.runStep(step, LoadCfStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("ИНФОРМАЦИЯ - Загрузка расширения из cfe-файла успешно завершена!", run);
    }

    @Test
    public void loadSrc(JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new LoadCfeStep();
        step.setSrc("src/cfe");
        step.setExtension("Extension1");
        step.setIbConnection("/Fbuild/ib");
        step.setLanguage("en");

        // when
        val run = r.runStep(step, LoadCfStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Сборка/загрузка расширения Extension1 завершена", run);
    }
}

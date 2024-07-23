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
        step.setIbcmd(true);
        step.setLanguage("en");

        // when
        val run = r.runStep(step, LoadCfStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Загрузка расширения из cfe завершена.", run);
    }

    @Test
    public void loadSrc(JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new LoadCfeStep();
        step.setSrc("src/cfe");
        step.setExtension("Extension1");
        step.setIbConnection("/Fbuild/ib");
        step.setIbcmd(true);
        step.setLanguage("en");

        // when
        val run = r.runStep(step, LoadCfStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Сборка расширения Extension1 из исходников завершена.", run);
    }
}

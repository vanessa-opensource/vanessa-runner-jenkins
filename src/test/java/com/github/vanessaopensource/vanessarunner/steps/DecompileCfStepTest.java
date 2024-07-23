package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
class DecompileCfStepTest {

    @Test
    public void decompileConfiguration(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new DecompileCfStep();
        step.setOut("src");
        step.setIbPath("build/ib");
        step.setIbcmd(true);
        step.setLanguage("en");

        // when
        val run = r.runStep(step, RunStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Разборка конфигурации текущей ИБ на исходники завершена.", run);
        r.assertChildFileExists("src/Configuration.xml", run);
    }
}

package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
class DecompileCfeStepTest {
    @Test
    public void decompileExtension(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new DecompileCfeStep();
        step.setExtension("testExtensionOK");
        step.setOut("src");
        step.setIbPath("build/ib");
        step.setIbcmd(true);
        step.setLanguage("en");

        // when
        val run = r.runStep(step, XUnitStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Разборка расширения на исходники завершена.", run);
        r.assertChildFileExists("src/Configuration.xml", run);
    }
}
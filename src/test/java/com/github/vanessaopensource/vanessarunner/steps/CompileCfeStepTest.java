package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins()
public class CompileCfeStepTest {

    @Test
    public void initSrc(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new CompileCfeStep();
        step.setSrc("src/cfe");
        step.setOut("1cv8_$version.cfe");
        step.setBuildNumber(9999);
        step.setIbcmd(true);
        step.setLanguage("en");

        // when
        val run = r.runStep(step, CompileCfeStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Сборка расширения из исходников в файл cfe завершена.", run);
        r.assertChildFileExists("1cv8_1.1.0.9999.cfe", run);
    }

    @Test
    public void initSrcWithBuldNumber(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new CompileCfeStep();
        step.setSrc("src/cfe");
        step.setOut("1cv8_$version.cfe");
        step.setWithBuildNumber(true);
        step.setIbcmd(true);
        step.setLanguage("en");

        // when
        val run = r.runStep(step, CompileCfeStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Сборка расширения из исходников в файл cfe завершена.", run);
        r.assertChildFileExists("1cv8_1.1.0.1.cfe", run);
    }
}

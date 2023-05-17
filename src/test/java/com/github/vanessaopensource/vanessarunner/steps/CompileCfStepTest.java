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
        step.setSrc("src/cf");
        step.setOut("1cv8_$version.cf");
        step.setBuildNumber(9999);
        step.setLanguage("en");

        // when
        val run = r.runStep(step, InitDevStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Выгрузка в файл завершена.", run);
        j.assertLogContains("Configuration successfully saved", run);
        r.assertChildFileExists("1cv8_1.0.0.9999.cf", run);
    }

    @Test
    void initSrcWithBuldNumber(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new CompileCfStep();
        step.setSrc("src/cf");
        step.setOut("1cv8_$version.cf");
        step.setWithBuildNumber(true);
        step.setLanguage("en");

        // when
        val run = r.runStep(step, InitDevStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Выгрузка в файл завершена.", run);
        j.assertLogContains("Configuration successfully saved", run);
        r.assertChildFileExists("1cv8_1.0.0.1.cf", run);
    }
}

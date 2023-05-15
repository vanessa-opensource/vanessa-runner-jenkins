package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

import java.util.List;

import static com.github.vanessaopensource.vanessarunner.steps.SyntaxCheckMode.*;


@WithJenkins
public class SyntaxCheckModeTest {

    @Test
    public void syntaxOK(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val modes = List.of(ThinClient, WebClient, Server, ExternalConnection);
        val step = new SyntaxCheckStep(modes);
        step.setReportAllure("build/allure.json");
        step.setIbPath("build/ib");
        step.setLanguage("en");

        // when
        val run = r.runStep(step, RunStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        r.assertChildFileExists("build/allure.json", run);
    }

    @Test
    public void syntaxFail(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val modes = List.of(ThinClient, WebClient, Server, ExternalConnection);
        val step = new SyntaxCheckStep(modes);
        step.setReportJUnit("build/junit.xml");
        step.setIbPath("build/ibFail");
        step.setLanguage("en");

        // when
        val run = r.runStep(step, SyntaxCheckModeTest.class);

        // then
        j.assertBuildStatus(Result.UNSTABLE, run);
        r.assertChildFileExists("build/junit.xml", run);
    }
}

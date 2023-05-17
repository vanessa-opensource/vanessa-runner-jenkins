package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

import java.util.List;

@WithJenkins
public class EDTValidateStepTest {

    @Test
    public void syntaxOK(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val projectList = List.of("cfOK");
        val step = new EDTValidateStep();
        step.setProjectList(projectList);
        step.setReportAllure("build/allure");

        // when
        val run = r.runStep(step, EDTValidateStepTest.class);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        r.assertChildFileExists("build/allure", run);
    }

    @Test
    public void syntaxFail(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val projectList = List.of("cfFail");
        val step = new EDTValidateStep();
        step.setProjectList(projectList);
        step.setReportJUnit("build/junit.xml");

        // when
        val run = r.runStep(step, EDTValidateStepTest.class);

        // then
        j.assertBuildStatus(Result.UNSTABLE, run);
        r.assertChildFileExists("build/junit.xml", run);
    }
}

package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class SessionKillStepTest {

    @Test
    public void killSessions(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new SessionKillStep("DefAlias");

        // when
        val run = r.runStep(step);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Сеансы запрещены", run);
        j.assertLogContains("Отключаю существующие сеансы", run);
    }

    @Test
    public void killSessionsWithNoLock(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new SessionKillStep("DefAlias");
        step.setKillWithNoLock(true);

        // when
        val run = r.runStep(step);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogNotContains("Сеансы запрещены", run);
        j.assertLogContains("Отключаю существующие сеансы", run);
    }

}

package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class SessionUnlockStepTest {
    @Test
    public void unlockSessions(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new SessionUnlockStep("DefAlias1");

        // when
        val run = r.runStep(step);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Сеансы разрешены", run);
    }
}

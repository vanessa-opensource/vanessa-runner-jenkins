package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class ScheduledJobsStepTest {

    @Test
    public void lockScheduledJobs(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new ScheduledJobsStep(true, "DefAlias");

        // when
        val run = r.runStep(step);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Регламентные задания запрещены", run);
    }

    @Test
    public void unlockScheduledJobs(final JenkinsRule j) throws Exception {
        val r = VRunnerRule.createRule(j);

        // given
        val step = new ScheduledJobsStep(false,"DefAlias");

        // when
        val run = r.runStep(step);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Регламентные задания разрешены", run);
    }
}

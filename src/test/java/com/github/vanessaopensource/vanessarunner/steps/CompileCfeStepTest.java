package com.github.vanessaopensource.vanessarunner.steps;

import hudson.model.Result;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins()
public class CompileCfeStepTest {

    @Test
    public void initSrc(JenkinsRule j) throws Exception {
        var r = VRunnerRule.createRule(j);

        // given
        var command = String.format("vrunnerCompileCfe src:'%s', out:'%s', language:'%s'",
                "src/cfe", "1cv8.cfe", "en");
        var script = VRunnerRule.buildScript(command);
        var job = r.createWorkFlowJob(script);
        var workSpace = r.createWorkSpace(job);
        VRunnerRule.createLocalData(CompileCfeStepTest.class, workSpace);

        // when
        var run = VRunnerRule.runJob(job);

        // then
        j.assertBuildStatus(Result.SUCCESS, run);
        j.assertLogContains("Выгрузка в файл завершена.", run);
        j.assertLogContains("Configuration successfully saved", run);
        VRunnerRule.assertChildFileExists("1cv8.cfe", workSpace);
    }
}

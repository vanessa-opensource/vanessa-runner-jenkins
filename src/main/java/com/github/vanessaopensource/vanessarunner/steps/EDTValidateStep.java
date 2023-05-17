package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.Messages;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunner;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerContext;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerEDT;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import hudson.model.Result;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.io.File;
import java.util.List;

/**
 * Validate the 1C:Enterprise Development Tools project.
 */
@Getter
@Setter
public final class EDTValidateStep extends VRunnerEDT {

    /**
     * A list of source directory locations of 1C:Enterprise Development Tools projects.
     * Use the following syntax: c:\\somewhere\\project1 c:\\somewhere\\project2.
     */
    @DataBoundSetter
    private List<String> projectList = List.of();

    /**
     * A list of 1C:Enterprise Development Tools source project names existing in the workspace.
     * Use the following syntax: project1 project2.
     */
    @DataBoundSetter
    private List<String> projectNameList = List.of();

    @DataBoundSetter
    private String reportAllure = "";

    @DataBoundSetter
    private String reportJUnit = "";

    @DataBoundSetter
    private String validationResult = "";

    @DataBoundConstructor
    public EDTValidateStep() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {

        context.putExitCodeResult(1, Result.UNSTABLE);

        context.setCommand("edt-validate");
        addProjectList(context);
        addProjectNameList(context);

        context.addParameter(reportAllure, "--allure-results2");
        context.addParameter(reportJUnit, "--junitpath");
        context.addParameter(validationResult, "--validation-result");

        super.setCommandContext(context);
    }

    private void addProjectList(final VRunnerContext context) {
        if (projectList.isEmpty()) {
            return;
        }
        context.setCommand("--project-list");
        projectList.forEach(x -> {
            val path = new File(x);
            if (path.isAbsolute()) {
                context.setCommand(x);
            } else {
                val child = context.workspaceChild(x);
                context.setCommand(child.getRemote());
            }
        });
    }

    private void addProjectNameList(final VRunnerContext context) {
        if (projectNameList.isEmpty()) {
            return;
        }
        context.setCommand("--project-name-list");
        projectNameList.forEach(context::setCommand);
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerEdtValidate";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("EDTValidateStep.DisplayName");
        }
    }
}

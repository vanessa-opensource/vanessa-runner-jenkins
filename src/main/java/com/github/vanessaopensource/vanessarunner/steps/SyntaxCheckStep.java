package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.Messages;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunner;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerContext;
import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerInfobase;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.AbortException;
import hudson.Extension;
import hudson.model.Result;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import java.util.List;

/**
 * Performs extended configuration check.
 */
@Getter
@Setter
public class SyntaxCheckStep extends VRunnerInfobase {

    /**
     * Performed checks.
     */
    @DataBoundSetter
    private final List<SyntaxCheckMode> mode;

    /**
     * Processes the extension with the specified name.
     */
    @DataBoundSetter
    private String extension = "";

    /**
     * Checks all extensions.
     */
    @DataBoundSetter
    private Boolean allExtensions = false;

    @DataBoundSetter
    private String reportAllure = "";

    @DataBoundSetter
    private String reportJUnit = "";

    @DataBoundSetter
    private Boolean groupByMetadata = false;

    @DataBoundConstructor
    public SyntaxCheckStep(final List<SyntaxCheckMode> mode) {
        super();
        this.mode = List.copyOf(mode);
    }

    @Override
    public void setCommandContext(final VRunnerContext context) throws AbortException {

        context.putExitCodeResult(1, Result.UNSTABLE);

        context.setCommand("syntax-check");

        context.addParameter(reportAllure, "--allure-results2");
        context.addParameter(reportJUnit, "--junitpath");
        context.addSwitch(groupByMetadata, "--groupbymetadat");

        super.setCommandContext(context);

        addParameterMode(context);
    }

    private void addParameterMode(final VRunnerContext context) {
        if (mode.isEmpty()) {
            return;
        }
        context.setCommand("--mode");
        mode.forEach(x -> x.addParameter(context));

        context.addParameter(extension, "-Extension");
        context.addSwitch(allExtensions, "-AllExtensions");
    }

    @Extension
    @SuppressWarnings("unused")
    public static final class DescriptorImpl extends VRunner.Descriptor {

        @Override
        public String getFunctionName() {
            return "vrunnerSyntaxCheck";
        }

        @NonNull
        @Override
        public String getDisplayName() {
            return Messages.getString("SyntaxCheckStep.DisplayName");
        }
    }
}

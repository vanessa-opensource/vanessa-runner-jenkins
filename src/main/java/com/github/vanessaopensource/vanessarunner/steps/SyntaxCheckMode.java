package com.github.vanessaopensource.vanessarunner.steps;

import com.github.vanessaopensource.vanessarunner.steps.core.VRunnerContext;

@SuppressWarnings("unused")
public enum SyntaxCheckMode {

    /**
     * Checks the logical integrity of the configuration.
     */
    ConfigLogIntegrity("-ConfigLogIntegrity"),

    /**
     * Searches for invalid references and references to deleted objects.
     */
    IncorrectReferences("-IncorrectReferences"),

    /**
     * Performs module syntax check for managed application environment emulation mode (thin client, file mode).
     */
    ThinClient("-ThinClient"),

    /**
     * Performs module syntax check in web client environment emulation mode.
     */
    WebClient("-WebClient"),

    /**
     * Performs module syntax check in 1C:Enterprise server environment emulation mode.
     */
    Server("-Server"),

    /**
     * Performs module syntax check in external connection environment emulation mode (file mode).
     */
    ExternalConnection("-ExternalConnection"),

    /**
     * Performs module syntax check in external connection environment emulation mode (client/server mode).
     */
    ExternalConnectionServer("-ExternalConnectionServer"),

    /**
     * Performs module syntax check in mobile client environment emulation mode.
     */
    MobileClient("-MobileClient"),

    /**
     * Performs module syntax check in standalone mobile client environment emulation mode.
     */
    MobileClientStandalone("-MobileClientStandalone"),

    /**
     * Validates the mobile client signature.
     */
    MobileClientDigiSign("-MobileClientDigiSign"),

    /**
     * Performs module syntax check in mobile application environment emulation mode (client mode).
     */
    MobileAppClient("-MobileAppClient"),

    /**
     * Performs module syntax check in mobile application environment emulation mode (server mode).
     */
    MobileAppServer("-MobileAppServer"),

    /**
     * Performs module syntax check in managed application environment emulation mode (thick client, file mode).
     */
    ThickClientManagedApplication("-ThickClientManagedApplication"),

    /**
     * Performs module syntax check in managed application environment emulation mode
     * (thick client, client/server mode).
     */
    ThickClientServerManagedApplication("-ThickClientServerManagedApplication"),

    /**
     * Performs module syntax check in ordinary application environment emulation mode (thick client, file mode).
     */
    ThickClientOrdinaryApplication("-ThickClientOrdinaryApplication"),

    /**
     * Performs module syntax check in ordinary application environment emulation mode
     * (thick client, client/server mode).
     */
    ThickClientServerOrdinaryApplication("-ThickClientServerOrdinaryApplication"),

    /**
     * Delivers modules without their sources.  If the configuration distribution settings specify that some modules
     * are delivered without their sources, check whether module images can be generated.
     */
    DistributiveModules("-DistributiveModules"),

    /**
     * Searches for local (not exported) procedures and functions that are never referenced,
     * including unused event handlers.
     */
    UnreferenceProcedures("-UnreferenceProcedures"),

    /**
     * Checks the availability of handlers assigned to interfaces, forms, and controls.
     */
    HandlersExistence("-HandlersExistence"),

    /**
     * Searches for event handlers that perform no actions. Such handlers can impact the system performance.
     */
    EmptyHandlers("-EmptyHandlers"),

    /**
     * Checks object method and object property calls using . (dot) for a limited set of types,
     * and check whether string literals that serve as parameters for certain functions, such as GetForm(), are valid.
     */
    ExtendedModulesCheck("-ExtendedModulesCheck"),

    /**
     * Searches the modules for modal method calls. Use this parameter only with -ExtendedModulesCheck parameter.
     */
    CheckUseModality("-CheckUseModality"),

    /**
     * Searches the modules for synchronous method calls. Use this parameter only with ExtendedModulesCheck parameter.
     */
    CheckUseSynchronousCalls("-CheckUseSynchronousCalls"),

    /**
     * Searches for functionality that cannot be executed in a mobile application.
     */
    UnsupportedFunctional("-UnsupportedFunctional");

    /**
     * Parameter value for vrunner.
     */
    private final String parameter;

    SyntaxCheckMode(final String parameter) {
        this.parameter = parameter;
    }

    /**
     * Adds parameter to vrunner context.
     *
     * @param context vrunner context
     */
    public void addParameter(final VRunnerContext context) {
        context.setCommand(parameter);
    }
}

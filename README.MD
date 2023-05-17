# Vanessa-runner plugin for Jenkins

Jenkins plugin to build, test & deploy configurations and extensions for [1C:Enterprise Platform](https://1c-dn.com/) with a help of [Vanessa-runner](https://github.com/vanessa-opensource/vanessa-runner) toolset.

See [examples pipelines](examples)

## Build

Plugin provides a set of steps to build various artifacts^
* `vrunnerCompileCf` - build configuration file from sources
* `vrunnerCompileCfe` - build configuration extension file from sources
* `vrunnerMakeDist` - create distribution files

# Test

Run BDD, Unit and Smoke tests with a help of [Vanessa-ADD](https://github.com/vanessa-opensource/add):
* `vanessaAdd` - run BDD test
* `vanessaXUnit` - run Unit and Smoke tests

Also, provides helper step:
* `vrunnerInitDev` - creates local file infobase to run tests

# Code quality
* `vrunnerSyntaxCheck` - performs extended configuration check.
* `vrunnerEdtValidate` - validate the 1C:Enterprise Development Tools project.

# Deploy
* `vrunnerLoadCf` - loads configuration from a file.
* `vrunnerLoadCfe` - loads configuration extension from a file.
* `vrunnerUpdateDist` - updates configuration from vendor distributive 
* `vrunnerUpdateDd` - apply changes
* `vrunnerRun` - run solution in client mode

Also, provides steps to manage production infobase:
* `vrunnerSessionLock` - lock new connections to infobase
* `vrunnerSessionKill` - kill current connections
* `vrunnerSessionUnlock` - unlock infobase for new connections
* `vrunnerScheduledJobs` - lock/unlock scheduled jobs

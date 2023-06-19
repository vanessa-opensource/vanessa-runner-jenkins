plugins {
    java
    id("org.jenkins-ci.jpi") version "0.48.0"
    id("io.franzbecker.gradle-lombok") version "5.0.0"
}

repositories {
    mavenCentral()
}

jenkinsPlugin {
    setProperty("jenkinsVersion", "2.401")

    displayName = "Vanessa-runner tool support"
    gitHubUrl = "https://github.com/vanessa-opensource/vanessa-runner-jenkins"

    dependencies {
        implementation("org.jenkins-ci.plugins.workflow", "workflow-step-api", "639.v6eca_cd8c04a_a_")
        implementation("org.jenkins-ci.plugins", "credentials", "1254.vb_96f366e7b_a_d")

        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-basic-steps", "1017.vb_45b_302f0cea_")
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-cps", "3691.v28b_14c465a_b_b_")
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-durable-task-step", "1247.v7f9dfea_b_4fd0")
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-job", "1308.v58d48a_763b_31")

        jenkinsServer("org.jenkinsci.plugins","pipeline-model-definition", "2.2141.v5402e818a_779")
        jenkinsServer("org.jenkins-ci.plugins","git", "5.1.0")
        jenkinsServer("org.jenkins-ci.plugins","junit", "1207.va_09d5100410f")
        jenkinsServer("org.jenkins-ci.plugins","copyartifact", "705.v5295cffec284")
    }

    gitVersion {
        allowDirty.set(true)
    }

    enableCheckstyle()
    enableJacoco()
    enableSpotBugs()
}

dependencies {
    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.9.3")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.9.3")
}

tasks.test {
    useJUnitPlatform()
}

tasks.server {
    execSpec {
        systemProperty("file.encoding", "UTF-8")
        systemProperty("hudson.plugins.git.GitSCM.ALLOW_LOCAL_CHECKOUT", "true")
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.generateGitVersion {
    doLast {
        project.version = outputFile.get().asFile.readText(Charsets.UTF_8)
    }
}

tasks.generateJenkinsManifest {
    dependsOn(tasks.generateGitVersion)
}

tasks.withType<Checkstyle> {
    ignoreFailures = true
}

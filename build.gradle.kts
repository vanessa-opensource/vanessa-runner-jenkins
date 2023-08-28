plugins {
    java
    id("org.jenkins-ci.jpi") version "0.49.0"
    id("io.franzbecker.gradle-lombok") version "5.0.0"
}

repositories {
    mavenCentral()
}

jenkinsPlugin {
    setProperty("jenkinsVersion", "2.414")

    displayName = "Vanessa-runner tool support"
    gitHubUrl = "https://github.com/vanessa-opensource/vanessa-runner-jenkins"

    dependencies {
        // https://plugins.jenkins.io/workflow-step-api/
        implementation("org.jenkins-ci.plugins.workflow", "workflow-step-api", "639.v6eca_cd8c04a_a_")
        // https://plugins.jenkins.io/credentials/
        implementation("org.jenkins-ci.plugins", "credentials", "1271.v54b_1c2c6388a_")

        // https://plugins.jenkins.io/workflow-basic-steps/
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-basic-steps", "1042.ve7b_140c4a_e0c")
        // https://plugins.jenkins.io/workflow-cps/
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-cps", "3773.v505e0052522c")
        // https://plugins.jenkins.io/workflow-durable-task-step/
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-durable-task-step", "1289.v4d3e7b_01546b_")
        // https://plugins.jenkins.io/workflow-job/
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-job", "1341.vd9fa_65f771dd")

        // https://plugins.jenkins.io/pipeline-model-definition/
        jenkinsServer("org.jenkinsci.plugins","pipeline-model-definition", "2.2144.v077a_d1928a_40")
        // https://plugins.jenkins.io/git/
        jenkinsServer("org.jenkins-ci.plugins","git", "5.2.0")
        // https://plugins.jenkins.io/junit/
        jenkinsServer("org.jenkins-ci.plugins","junit", "1217.v4297208a_a_b_ce")
        // https://plugins.jenkins.io/copyartifact/
        jenkinsServer("org.jenkins-ci.plugins","copyartifact", "714.v28a_34f8c563f")
    }

    gitVersion {
        allowDirty.set(true)
        sanitize.set(true)
    }

    enableCheckstyle()
    enableJacoco()
    enableSpotBugs()
}

dependencies {
    // Added to run compileJava task
    compileOnly("com.google.j2objc:j2objc-annotations:2.8")
    compileOnly("org.checkerframework:checker-qual:3.33.0")
    annotationProcessor("org.checkerframework:checker-qual:3.33.0")

    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.10.0")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.10.0")
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
        project.version = outputFile.get().asFile.readLines(Charsets.UTF_8)[0]
    }
}

tasks.generateJenkinsManifest {
    dependsOn(tasks.generateGitVersion)
}

tasks.withType<Checkstyle> {
    ignoreFailures = true
}

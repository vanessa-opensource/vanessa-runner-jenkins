plugins {
    java
    id("org.jenkins-ci.jpi") version "0.51.0"
    id("io.franzbecker.gradle-lombok") version "5.0.0"
}

repositories {
    mavenCentral()
}

jenkinsPlugin {
    setProperty("jenkinsVersion", "2.452")

    displayName = "Vanessa-runner tool support"
    gitHub = uri("https://github.com/vanessa-opensource/vanessa-runner-jenkins")

    dependencies {
        // https://plugins.jenkins.io/workflow-step-api/
        implementation("org.jenkins-ci.plugins.workflow", "workflow-step-api", "678.v3ee58b_469476")
        // https://plugins.jenkins.io/credentials/
        implementation("org.jenkins-ci.plugins", "credentials", "1371.vfee6b_095f0a_3")

        // https://plugins.jenkins.io/workflow-basic-steps/
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-basic-steps", "1058.vcb_fc1e3a_21a_9")
        // https://plugins.jenkins.io/workflow-cps/
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-cps", "3908.vd6b_b_5a_a_54010")
        // https://plugins.jenkins.io/workflow-durable-task-step/
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-durable-task-step", "1360.v82d13453da_a_f")
        // https://plugins.jenkins.io/workflow-job/
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-job", "1400.v7fd111b_ec82f")

        // https://plugins.jenkins.io/pipeline-model-definition/
        jenkinsServer("org.jenkinsci.plugins","pipeline-model-definition", "2.2205.vc9522a_9d5711")
        // https://plugins.jenkins.io/git/
        jenkinsServer("org.jenkins-ci.plugins","git", "5.2.0")
        // https://plugins.jenkins.io/junit/
        jenkinsServer("org.jenkins-ci.plugins","junit", "1240.vf9529b_881428")
        // https://plugins.jenkins.io/copyartifact/
        jenkinsServer("org.jenkins-ci.plugins","copyartifact", "722.v0662a_9b_e22a_c")
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

    generatedJenkinsTestImplementation("org.checkerframework:checker-qual:3.33.0")
    generatedJenkinsTestImplementation("com.google.j2objc:j2objc-annotations:2.8")
    testCompileOnly("org.checkerframework:checker-qual:3.33.0")
    testImplementation("com.google.j2objc:j2objc-annotations:2.8")
    testImplementation("org.checkerframework:checker-qual:3.33.0")

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

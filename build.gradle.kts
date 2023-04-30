plugins {
    java
    id("com.github.spotbugs") version "5.0.14"
    id("org.jenkins-ci.jpi") version "0.48.0"
    id("io.franzbecker.gradle-lombok") version "5.0.0"
}

repositories {
    mavenCentral()
}

jenkinsPlugin {
    setProperty("jenkinsVersion", "2.375")

    displayName = "Vanessa-runner tool support"
    gitHubUrl = "https://github.com/vanessa-opensource/vanessa-runner-jenkins"

    dependencies {
        implementation("org.jenkins-ci.plugins.workflow", "workflow-step-api", "639.v6eca_cd8c04a_a_")
        implementation("org.jenkins-ci.plugins", "credentials", "1224.vc23ca_a_9a_2cb_0")

        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-basic-steps", "991.v43d80fea_ff66")
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-cps", "3641.vf58904a_b_b_5d8")
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-durable-task-step", "1199.v02b_9244f8064")
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-job", "1284.v2fe8ed4573d4")

        jenkinsServer("org.jenkinsci.plugins","pipeline-model-definition", "2.2125.vddb_a_44a_d605e")
        jenkinsServer("org.jenkins-ci.plugins","git", "5.0.0")
        jenkinsServer("org.jenkins-ci.plugins","junit", "1198.ve38db_d1b_c975")
        jenkinsServer("org.jenkins-ci.plugins","copyartifact", "698.v393f578eb_ddc")
    }

    gitVersion {
        allowDirty.set(true)
    }
}

dependencies {
    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.9.1")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.9.1")
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

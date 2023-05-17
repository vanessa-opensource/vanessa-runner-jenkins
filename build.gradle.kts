plugins {
    java
    id("org.jenkins-ci.jpi") version "0.48.0"
    id("io.franzbecker.gradle-lombok") version "5.0.0"
}

repositories {
    mavenCentral()
}

jenkinsPlugin {
    setProperty("jenkinsVersion", "2.387")

    displayName = "Vanessa-runner tool support"
    gitHubUrl = "https://github.com/vanessa-opensource/vanessa-runner-jenkins"

    dependencies {
        implementation("org.jenkins-ci.plugins.workflow", "workflow-step-api", "639.v6eca_cd8c04a_a_")
        implementation("org.jenkins-ci.plugins", "credentials", "1224.vc23ca_a_9a_2cb_0")

        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-basic-steps", "1017.vb_45b_302f0cea_")
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-cps", "3659.v582dc37621d8")
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-durable-task-step", "1246.v5524618ea_097")
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-job", "1292.v27d8cc3e2602")

        jenkinsServer("org.jenkinsci.plugins","pipeline-model-definition", "2.2131.vb_9788088fdb_5")
        jenkinsServer("org.jenkins-ci.plugins","git", "5.0.2")
        jenkinsServer("org.jenkins-ci.plugins","junit", "1202.v79a_986785076")
        jenkinsServer("org.jenkins-ci.plugins","copyartifact", "698.v393f578eb_ddc")
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

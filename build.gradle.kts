plugins {
    java
    id("com.github.spotbugs") version "5.0.14"
    id("org.jenkins-ci.jpi") version "0.47.0"
    id("io.franzbecker.gradle-lombok") version "5.0.0"
}

repositories {
    mavenCentral()
}

jenkinsPlugin {
    setProperty("jenkinsVersion", "2.375")

    displayName = "Vanessa-runner tool support"

    description = "Provide steps ant tools for vanessa-runner"
    gitHubUrl = "https://github.com/vanessa-opensource/vanessa-runner"

    dependencies {
        implementation("org.jenkins-ci.plugins.workflow", "workflow-step-api", "639.v6eca_cd8c04a_a_")
        implementation("org.jenkins-ci.plugins", "credentials", "1224.vc23ca_a_9a_2cb_0")

        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-basic-steps", "991.v43d80fea_ff66")
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-cps", "3641.vf58904a_b_b_5d8")
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-durable-task-step", "1199.v02b_9244f8064")
        testImplementation("org.jenkins-ci.plugins.workflow", "workflow-job", "1284.v2fe8ed4573d4")
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
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
plugins {
    application
}
repositories {
    mavenCentral()
}
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}
application {
    mainClass = "interpreter.Main"
}
tasks.named<JavaExec>("run") {
    standardInput = System.`in` // keyboard input for the text menu
}
tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
    workingDir = rootDir // new: test.in and log files in the project root
}

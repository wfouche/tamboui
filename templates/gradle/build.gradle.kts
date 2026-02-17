plugins {
    java
    application
    id("com.gradleup.shadow") version "9.3.1"
}

group = "dev.tamboui"
version = "1.0.0-SNAPSHOT"

description = "A minimal TamboUI Toolkit application template"

repositories {
    mavenCentral()
    maven {
        url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        mavenContent {
            snapshotsOnly()
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

dependencies {
    // TamboUI Toolkit - High-level DSL for building TUI applications
    // Note: Replace "0.1.0-SNAPSHOT" with the latest snapshot version from:
    // https://central.sonatype.com/repository/maven-snapshots/dev/tamboui/
    implementation("dev.tamboui:tamboui-toolkit:0+")

    // TamboUI Panama Backend - Terminal backend implementation, requiring Java 22+
    runtimeOnly("dev.tamboui:tamboui-panama-backend:0+")
    // TamboUI JLine3 Backend - Terminal backend implementation
    runtimeOnly("dev.tamboui:tamboui-jline3-backend:0+")
}

application {
    mainClass.set("dev.tamboui.HelloToolkitApp")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

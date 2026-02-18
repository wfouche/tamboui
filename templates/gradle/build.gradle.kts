plugins {
    java
    application
    id("com.gradleup.shadow") version "9.3.1"
}

group = "com.example"
version = "0.1.0-SNAPSHOT"

description = "A minimal TamboUI Toolkit application template for Gradle"

val tambouiVersion: String by project

repositories {
    mavenCentral()
    maven {
        // Only needed for snapshot versions
        url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        mavenContent {
            snapshotsOnly()
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    // TamboUI Toolkit - High-level DSL for building TUI applications
    implementation("dev.tamboui:tamboui-toolkit:${tambouiVersion}")
    // TamboUI Panama Backend - Terminal backend implementation, requiring Java 22+
    //runtimeOnly("dev.tamboui:tamboui-panama-backend:${tambouiVersion}")
    // TamboUI JLine3 Backend - Terminal backend implementation
    runtimeOnly("dev.tamboui:tamboui-jline3-backend:${tambouiVersion}")
}

application {
    mainClass.set("dev.tamboui.HelloToolkitApp")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

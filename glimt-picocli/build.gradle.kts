plugins {
    id("ink.glimt.java-library")
}

description = "PicoCLI integration for Glimt TUI applications"

dependencies {
    api(projects.glimtTui)
    api(libs.picocli)
}

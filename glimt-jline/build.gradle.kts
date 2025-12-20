plugins {
    id("ink.glimt.java-library")
}

description = "JLine 3 backend for Glimt TUI library"

dependencies {
    api(projects.glimtCore)
    api(libs.jline)
}

plugins {
    id("dev.tamboui.java-library")
}

description = "Core types and abstractions for TamboUI TUI library"

dependencies {
    // Use tamboui-core-assertj only in tests
    testImplementation(projects.tambouiCoreAssertj)
}

plugins {
    id("dev.tamboui.java-library")
}

description = "AssertJ custom assertions for TamboUI"

dependencies {
    val libs = versionCatalogs.named("libs")
    
    // Depend on tamboui-core for Buffer and related types
    api(projects.tambouiCore)
    
    // AssertJ is needed as API dependency for custom assertions
    api(libs.findLibrary("assertj-core").orElseThrow())
}



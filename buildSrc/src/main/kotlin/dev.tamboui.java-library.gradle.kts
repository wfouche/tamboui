plugins {
    `java-library`
    id("dev.tamboui.publishing")
}

dependencies {
    val libs = versionCatalogs.named("libs")
    testImplementation(platform(libs.findLibrary("junit.bom").orElseThrow()))
    testImplementation(libs.findBundle("testing").orElseThrow())
}

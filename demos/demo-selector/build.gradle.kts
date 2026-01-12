plugins {
    id("dev.tamboui.demo-project")
}

description = "Interactive demo selector"

demo {
    displayName = "Demo Selector"
    internal = true
}

dependencies {
    implementation(projects.tambouiToolkit)
    implementation(libs.gradle.tooling.api)
}

application {
    mainClass.set("dev.tamboui.demo.DemoSelector")
}

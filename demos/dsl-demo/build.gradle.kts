plugins {
    id("ink.glimt.demo-project")
}

description = "Demo showcasing the DSL module with Widget Playground"

dependencies {
    implementation(projects.glimtDsl)
}

application {
    mainClass.set("ink.glimt.demo.DslDemo")
}

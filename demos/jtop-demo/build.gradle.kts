plugins {
    id("ink.glimt.demo-project")
}

description = "JTop - System monitor demo using the DSL module"

dependencies {
    implementation(projects.glimtDsl)
}

application {
    mainClass.set("ink.glimt.demo.JTopDemo")
}

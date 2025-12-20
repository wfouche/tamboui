plugins {
    id("ink.glimt.demo-project")
}

description = "Demo showcasing the TuiRunner framework"

dependencies {
    implementation(projects.glimtTui)
}

application {
    mainClass.set("ink.glimt.demo.TuiDemo")
}

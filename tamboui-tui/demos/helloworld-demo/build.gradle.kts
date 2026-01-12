plugins {
    id("dev.tamboui.demo-project")
}

description = "Minimal Hello World demo using TuiRunner"

demo {
    tags = setOf("tui-runner", "paragraph", "minimal")
}

dependencies {
    implementation(project(":tamboui-tui"))
}

application {
    mainClass = "dev.tamboui.demo.HelloWorldDemo"
}


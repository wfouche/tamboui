plugins {
    id("dev.tamboui.demo-project")
}

description = "Demo showcasing dynamic layout switching between Flow, Dock, Grid, and Columns"

demo {
    tags = setOf("toolkit", "layout", "flow", "dock", "grid", "columns")
}

dependencies {
    implementation(projects.tambouiToolkit)
}

application {
    mainClass.set("dev.tamboui.demo.LayoutDemo")
}

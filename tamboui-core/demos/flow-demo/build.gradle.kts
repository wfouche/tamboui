plugins {
    id("dev.tamboui.demo-project")
}

description = "Demo showcasing the Flow layout with wrap layout"

demo {
    displayName = "Flow Layout"
    tags = setOf("flow", "layout", "wrap")
}

application {
    mainClass.set("dev.tamboui.demo.FlowDemo")
}

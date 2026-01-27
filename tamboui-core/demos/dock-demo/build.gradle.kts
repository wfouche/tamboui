plugins {
    id("dev.tamboui.demo-project")
}

description = "Demo showcasing the Dock layout with 5-region layout"

demo {
    displayName = "Dock Layout"
    tags = setOf("dock", "layout", "border-layout")
}

application {
    mainClass.set("dev.tamboui.demo.DockDemo")
}

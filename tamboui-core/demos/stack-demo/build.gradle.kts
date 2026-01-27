plugins {
    id("dev.tamboui.demo-project")
}

description = "Demo showcasing the Stack layout with overlapping layers"

demo {
    displayName = "Stack Layout"
    tags = setOf("stack", "layout", "overlay")
}

application {
    mainClass.set("dev.tamboui.demo.StackDemo")
}

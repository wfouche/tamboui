plugins {
    id("dev.tamboui.demo-project")
}

description = "Demo showcasing Grid layout with CSS Grid-inspired layout"

demo {
    displayName = "Grid Layout"
    tags = setOf("grid", "layout", "block", "paragraph")
}

application {
    mainClass.set("dev.tamboui.demo.GridDemo")
}

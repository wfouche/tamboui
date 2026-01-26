plugins {
    id("dev.tamboui.demo-project")
}

description = "Demo showcasing Grid widget with CSS Grid-inspired layout"

demo {
    tags = setOf("grid", "layout", "block", "paragraph")
}

application {
    mainClass.set("dev.tamboui.demo.GridDemo")
}

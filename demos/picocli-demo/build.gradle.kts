plugins {
    id("ink.glimt.demo-project")
}

description = "Demo showcasing PicoCLI integration with Glimt"

dependencies {
    implementation(projects.glimtPicocli)
    annotationProcessor(libs.picocli.codegen)
}

application {
    mainClass.set("ink.glimt.demo.PicoCLIDemo")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf(
        "-Aproject=${project.group}/${project.name}",
        "-Xlint:-processing"  // Suppress annotation processor warnings
    ))
}

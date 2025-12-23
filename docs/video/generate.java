///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS dev.jbang:jash:LATEST

import static java.lang.IO.*;

import java.io.IOException;

import static dev.jbang.jash.Jash.*;

void main(String... args) throws IOException {
    println("Generating video...");

    Path videosDir = Path.of(".");

    Path outputDir = videosDir.resolve("output");

    String pattern = args.length > 0 ? args[0] : ".*\\.tape$";
    Pattern filter = Pattern.compile(pattern);

    for (Path path : Files.list(videosDir).toList()) {
        if (filter.matcher(path.getFileName().toString()).matches()) {
            if(path.getFileName().toString().endsWith("_.tape")) {
                continue; // ignore shared tapes
            }
            println("Processing video: " + path.getFileName());

            Path outputPath = outputDir.resolve(path.getFileName().toString().replace(".tape", ".svg"));

            // INSERT_YOUR_CODE
            // Only generate if path is newer than outputPath
            if (Files.exists(outputPath)
                    && Files.getLastModifiedTime(outputPath).toMillis() >= Files.getLastModifiedTime(path).toMillis()) {
                println("  (up to date: skipping)");
                continue;
            }

            var cmd = start("vhs", "-o", outputPath.toString(), path.toString())

                    .stream()
                    .peek(System.out::println)
                    .count();
            println("Video processed: " + path.getFileName());
        }
    }

    Path htmlOutputDir = outputDir;
    Files.createDirectories(htmlOutputDir); // make sure it exists

    println("Generating index.html...");

    // Generate index.html showing all SVGs in outputDir
    Path indexFile = htmlOutputDir.resolve("index.html");
    try (var writer = Files.newBufferedWriter(indexFile)) {
        writer.write(
                """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <title>Demo Videos</title>
                            <style>
                                body { font-family: sans-serif; background: #222; color: #fafafa; margin: 2em; }
                                .gallery { display: flex; flex-wrap: wrap; gap: 2em; }
                                .item { background: #333; padding: 1em; border-radius: 8px; box-shadow: 0 2px 12px #0003; }
                                .item h2 { font-size: 1em; margin-bottom: 0.5em; color: #ffb347; }
                                .item img { width: 600px; max-width: 100vw; background: #222; display: block; cursor: pointer; }
                                .modal { display: none; position: fixed; z-index: 1000; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.9); }
                                .modal.active { display: flex; align-items: center; justify-content: center; }
                                .modal-content { max-width: 90vw; max-height: 90vh; background: #222; padding: 2em; border-radius: 8px; position: relative; }
                                .modal-content img { max-width: 100%; max-height: 90vh; display: block; }
                                .modal-close { position: absolute; top: 10px; right: 20px; color: #fafafa; font-size: 2em; font-weight: bold; cursor: pointer; line-height: 1; }
                                .modal-close:hover { color: #ffb347; }
                            </style>
                        </head>
                        <body>
                            <h1>TamboUI Demo Videos</h1>
                            <div class="gallery">
                        """);

        try (var files = Files.list(outputDir)) {
            files.filter(f -> f.toString().endsWith(".svg")).sorted().forEach(svg -> {
                String name = svg.getFileName().toString();
                String baseName = name.replaceAll("\\.svg$", "");
                try {
                    writer.write("<div class=\"item\">\n");
                    writer.write("  <h2>" + baseName + "</h2>\n");
                    writer.write("  <img src=\"" + name + "\" alt=\"" + baseName + "\" onclick=\"openModal('" + name
                            + "')\">\n");
                    writer.write("</div>\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        writer.write("""
                    </div>
                    <div id="modal" class="modal" onclick="closeModal()">
                        <div class="modal-content" onclick="event.stopPropagation()">
                            <span class="modal-close" onclick="closeModal()">&times;</span>
                            <img id="modal-img" src="" alt="">
                        </div>
                    </div>
                    <script>
                        function openModal(imgSrc) {
                            document.getElementById('modal-img').src = imgSrc;
                            document.getElementById('modal').classList.add('active');
                        }
                        function closeModal() {
                            document.getElementById('modal').classList.remove('active');
                        }
                        document.addEventListener('keydown', function(e) {
                            if (e.key === 'Escape') {
                                closeModal();
                            }
                        });
                    </script>
                </body>
                </html>
                """);
    }
}

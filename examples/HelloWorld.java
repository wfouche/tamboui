//DEPS ink.glimt:glimt-dsl:LATEST
//JAVA 11+

import ink.glimt.tui.TuiRunner;
import ink.glimt.tui.Keys;
import ink.glimt.widgets.paragraph.Paragraph;
import ink.glimt.text.Text;

public class HelloWorld {
    public static void main(String[] args) throws Exception {
        try (var tui = TuiRunner.create()) {
            tui.run(
                (event, runner) -> {
                    if (Keys.isQuit(event)) {
                        runner.quit();
                        return false;
                    }
                    return false;
                },
                frame -> {
                    var paragraph = Paragraph.builder()
                        .text(Text.from("Hello, Glimt! Press 'q' to quit."))
                        .build();
                    frame.renderWidget(paragraph, frame.area());
                }
            );
        }
    }
}
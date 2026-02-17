///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS dev.tamboui:tamboui-toolkit:LATEST
//DEPS dev.tamboui:tamboui-jline3-backend:LATEST
//REPOS https://central.sonatype.com/repository/maven-snapshots/
/*
 * Copyright TamboUI Contributors
 * SPDX-License-Identifier: MIT
 */
package dev.tamboui;

import static dev.tamboui.toolkit.Toolkit.*;

import dev.tamboui.toolkit.app.ToolkitApp;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;

/**
 * A minimal Hello World application using TamboUI Toolkit.
 * <p>
 * This demonstrates the basic structure of a TamboUI Toolkit application:
 * <ul>
 *   <li>Extend {@code ToolkitApp} to create your application</li>
 *   <li>Override {@code render()} to define your UI</li>
 *   <li>Add event handlers to elements for interactivity</li>
 *   <li>Call {@code run()} from {@code main()} to start the application</li>
 * </ul>
 * <p>
 * Run this file directly with JBang:
 * <pre>{@code
 * jbang HelloToolkitApp.java
 * }</pre>
 */
public class HelloToolkitApp extends ToolkitApp {

    /**
     * Application entry point.
     *
     * @param args command line arguments
     * @throws Exception if an error occurs
     */
    public static void main(String[] args) throws Exception {
        new HelloToolkitApp().run();
    }

    /**
     * Renders the application UI.
     * This method is called each frame to get the current state.
     *
     * @return the root element to render
     */
    @Override
    protected Element render() {
        return panel("Hello TamboUI",
                text("Welcome to TamboUI Toolkit!").bold().cyan(),
                text(""),
                text("Press 'q' or Ctrl+C to quit").dim()
        )
        .rounded()
        .id("main")
        .focusable()
        .onKeyEvent(event -> {
            if (event.isQuit()) {
                quit();
                return EventResult.HANDLED;
            }
            return EventResult.UNHANDLED;
        });
    }
}

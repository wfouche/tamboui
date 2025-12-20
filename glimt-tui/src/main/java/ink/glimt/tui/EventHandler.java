/*
 * Copyright (c) 2025 Glimt Contributors
 * SPDX-License-Identifier: MIT
 */
package ink.glimt.tui;

import ink.glimt.tui.event.Event;

/**
 * Functional interface for handling TUI events.
 * <p>
 * The event handler is called for each event received by the application.
 * Return {@code true} to trigger a redraw, or {@code false} to skip redrawing.
 *
 * @see TuiRunner#run(EventHandler, Renderer)
 */
@FunctionalInterface
public interface EventHandler {

    /**
     * Handles an event.
     *
     * @param event  the event to handle
     * @param runner the TUI runner (can be used to call {@link TuiRunner#quit()})
     * @return {@code true} if the UI should be redrawn, {@code false} otherwise
     */
    boolean handle(Event event, TuiRunner runner);
}

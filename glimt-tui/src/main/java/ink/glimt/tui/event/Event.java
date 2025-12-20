/*
 * Copyright (c) 2025 Glimt Contributors
 * SPDX-License-Identifier: MIT
 */
package ink.glimt.tui.event;

/**
 * Base interface for all TUI events.
 * <p>
 * Events are delivered to the application through the event handler.
 * This is a sealed interface with the following implementations:
 * <ul>
 *   <li>{@link KeyEvent} - Keyboard input</li>
 *   <li>{@link MouseEvent} - Mouse input</li>
 *   <li>{@link ResizeEvent} - Terminal window resize</li>
 *   <li>{@link TickEvent} - Animation timer tick</li>
 * </ul>
 *
 * @see ink.glimt.tui.EventHandler
 */
public interface Event {
}

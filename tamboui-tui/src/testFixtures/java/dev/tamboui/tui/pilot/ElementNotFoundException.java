/*
 * Copyright TamboUI Contributors
 * SPDX-License-Identifier: MIT
 */
package dev.tamboui.tui.pilot;

/**
 * Thrown when an element cannot be found by ID (e.g. when using
 * {@link Pilot#findElement(String)} or {@link Pilot#click(String)}).
 */
public class ElementNotFoundException extends Exception {

    /**
     * Creates an exception with the given message.
     *
     * @param message the detail message
     */
    public ElementNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates an exception with the given message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public ElementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

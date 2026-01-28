package dev.tamboui.layout;

import dev.tamboui.errors.TamboUIException;

/**
 * Exception for layout related errors.
 */
public class LayoutException extends TamboUIException {
    
    public LayoutException(String message) {
        super(message);
    }

    public LayoutException(String message, Throwable cause) {
        super(message, cause);
    }
}

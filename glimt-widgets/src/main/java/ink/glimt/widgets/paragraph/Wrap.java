/*
 * Copyright (c) 2025 Glimt Contributors
 * SPDX-License-Identifier: MIT
 */
package ink.glimt.widgets.paragraph;

/**
 * Text wrapping mode for paragraphs.
 */
public enum Wrap {
    /**
     * No wrapping - text extends beyond bounds.
     */
    NONE,

    /**
     * Wrap at word boundaries.
     */
    WORD,

    /**
     * Wrap at character boundaries.
     */
    CHARACTER
}

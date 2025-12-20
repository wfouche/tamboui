/*
 * Copyright (c) 2025 Glimt Contributors
 * SPDX-License-Identifier: MIT
 */
package ink.glimt.widgets;

import ink.glimt.buffer.Buffer;
import ink.glimt.layout.Rect;

/**
 * A widget that can render itself to a buffer.
 */
@FunctionalInterface
public interface Widget {

    /**
     * Renders this widget to the given buffer area.
     */
    void render(Rect area, Buffer buffer);
}

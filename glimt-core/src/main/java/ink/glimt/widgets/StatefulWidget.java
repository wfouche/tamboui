/*
 * Copyright (c) 2025 Glimt Contributors
 * SPDX-License-Identifier: MIT
 */
package ink.glimt.widgets;

import ink.glimt.buffer.Buffer;
import ink.glimt.layout.Rect;

/**
 * A widget that maintains state between renders.
 *
 * @param <S> the state type
 */
public interface StatefulWidget<S> {

    /**
     * Renders this widget to the given buffer area with the provided state.
     */
    void render(Rect area, Buffer buffer, S state);
}

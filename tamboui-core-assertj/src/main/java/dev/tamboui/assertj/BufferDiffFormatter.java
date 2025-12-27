/*
 * Copyright (c) 2025 TamboUI Contributors
 * SPDX-License-Identifier: MIT
 */
package dev.tamboui.assertj;

import dev.tamboui.buffer.Buffer;
import dev.tamboui.buffer.Cell;
import dev.tamboui.layout.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for formatting buffer differences in a readable way,
 * similar to ratatui.rs assertion output.
 */
final class BufferDiffFormatter {

    private BufferDiffFormatter() {
        // Utility class
    }

    /**
     * Formats a diff between two buffers, showing both buffers side-by-side.
     *
     * @param actual the actual buffer
     * @param expected the expected buffer
     * @return a formatted string showing the difference
     */
    static String formatDiff(Buffer actual, Buffer expected) {
        StringBuilder sb = new StringBuilder();

        // Format actual buffer
        sb.append(" left: ").append(formatBuffer(actual)).append("\n");
        sb.append(" right: ").append(formatBuffer(expected));

        return sb.toString();
    }

    /**
     * Formats a buffer for display, similar to ratatui.rs format.
     *
     * @param buffer the buffer to format
     * @return a formatted string representation
     */
    static String formatBuffer(Buffer buffer) {
        if (buffer == null) {
            return "null";
        }

        StringBuilder sb = new StringBuilder();
        Rect area = buffer.area();

        // Buffer header
        sb.append("Buffer {\n");
        sb.append("    area: ").append(formatRect(area)).append(",\n");

        // Content lines
        List<String> contentLines = formatContentLines(buffer);
        sb.append("    content: [\n");
        for (int i = 0; i < contentLines.size(); i++) {
            sb.append("        \"").append(contentLines.get(i)).append("\"");
            if (i < contentLines.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("    ],\n");

        // Styles (simplified - could be enhanced to show style differences)
        sb.append("    styles: [\n");
        sb.append("        x: 0, y: 0, fg: Reset, bg: Reset, modifier: NONE,\n");
        sb.append("    ]\n");
        sb.append("}");

        return sb.toString();
    }

    private static String formatRect(Rect rect) {
        return String.format("Rect { x: %d, y: %d, width: %d, height: %d }",
            rect.x(), rect.y(), rect.width(), rect.height());
    }

    private static List<String> formatContentLines(Buffer buffer) {
        List<String> lines = new ArrayList<>();
        Rect area = buffer.area();

        for (int y = area.top(); y < area.bottom(); y++) {
            StringBuilder line = new StringBuilder();
            for (int x = area.left(); x < area.right(); x++) {
                Cell cell = buffer.get(x, y);
                String symbol = cell.symbol();
                // Escape quotes in the symbol
                symbol = symbol.replace("\"", "\\\"");
                line.append(symbol);
            }
            lines.add(line.toString());
        }

        return lines;
    }
}



/*
 * Copyright (c) 2025 TamboUI Contributors
 * SPDX-License-Identifier: MIT
 */
package dev.tamboui.css.property;

import dev.tamboui.widgets.block.BorderType;

import java.util.Map;
import java.util.Optional;

/**
 * Converts CSS border-type values to BorderType enum.
 * <p>
 * Supports the following values:
 * <ul>
 *   <li>{@code "plain"} - plain borders</li>
 *   <li>{@code "rounded"} - rounded corners</li>
 *   <li>{@code "double"} - double-line borders</li>
 *   <li>{@code "thick"} - thick borders</li>
 *   <li>{@code "light-double-dashed"} - light double-dashed borders</li>
 *   <li>{@code "heavy-double-dashed"} - heavy double-dashed borders</li>
 *   <li>{@code "light-triple-dashed"} - light triple-dashed borders</li>
 *   <li>{@code "heavy-triple-dashed"} - heavy triple-dashed borders</li>
 *   <li>{@code "light-quadruple-dashed"} - light quadruple-dashed borders</li>
 *   <li>{@code "heavy-quadruple-dashed"} - heavy quadruple-dashed borders</li>
 *   <li>{@code "quadrant-inside"} - quadrant inside borders</li>
 *   <li>{@code "quadrant-outside"} - quadrant outside borders</li>
 * </ul>
 */
public final class BorderTypeConverter implements PropertyConverter<BorderType> {

    @Override
    public Optional<BorderType> convert(String value, Map<String, String> variables) {
        if (value == null || value.trim().isEmpty()) {
            return Optional.empty();
        }

        String resolved = PropertyConverter.resolveVariables(value.trim(), variables).toLowerCase();

        switch (resolved) {
            case "plain":
                return Optional.of(BorderType.PLAIN);
            case "rounded":
                return Optional.of(BorderType.ROUNDED);
            case "double":
                return Optional.of(BorderType.DOUBLE);
            case "thick":
                return Optional.of(BorderType.THICK);
            case "light-double-dashed":
                return Optional.of(BorderType.LIGHT_DOUBLE_DASHED);
            case "heavy-double-dashed":
                return Optional.of(BorderType.HEAVY_DOUBLE_DASHED);
            case "light-triple-dashed":
                return Optional.of(BorderType.LIGHT_TRIPLE_DASHED);
            case "heavy-triple-dashed":
                return Optional.of(BorderType.HEAVY_TRIPLE_DASHED);
            case "light-quadruple-dashed":
                return Optional.of(BorderType.LIGHT_QUADRUPLE_DASHED);
            case "heavy-quadruple-dashed":
                return Optional.of(BorderType.HEAVY_QUADRUPLE_DASHED);
            case "quadrant-inside":
                return Optional.of(BorderType.QUADRANT_INSIDE);
            case "quadrant-outside":
                return Optional.of(BorderType.QUADRANT_OUTSIDE);
            default:
                return Optional.empty();
        }
    }
}

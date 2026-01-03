/*
 * Copyright (c) 2025 TamboUI Contributors
 * SPDX-License-Identifier: MIT
 */
package dev.tamboui.css.cascade;

import dev.tamboui.style.Width;
import dev.tamboui.layout.Alignment;
import dev.tamboui.style.Color;
import dev.tamboui.style.Modifier;
import dev.tamboui.style.Style;
import dev.tamboui.widgets.block.BorderType;
import dev.tamboui.widgets.block.Padding;

import java.util.*;

/**
 * Represents the computed style for an element after cascade resolution.
 * <p>
 * Contains all resolved CSS properties that can be applied to an element.
 */
public final class ResolvedStyle {

    private final Color foreground;
    private final Color background;
    private final Set<Modifier> modifiers;
    private final Padding padding;
    private final Alignment alignment;
    private final BorderType borderType;
    private final Width width;
    private final Map<String, String> additionalProperties;

    private ResolvedStyle(Color foreground,
                          Color background,
                          Set<Modifier> modifiers,
                          Padding padding,
                          Alignment alignment,
                          BorderType borderType,
                          Width width,
                          Map<String, String> additionalProperties) {
        this.foreground = foreground;
        this.background = background;
        this.modifiers = modifiers != null
                ? Collections.unmodifiableSet(EnumSet.copyOf(modifiers))
                : Collections.<Modifier>emptySet();
        this.padding = padding;
        this.alignment = alignment;
        this.borderType = borderType;
        this.width = width;
        this.additionalProperties = Collections.unmodifiableMap(new HashMap<>(additionalProperties));
    }

    /**
     * Creates an empty resolved style.
     */
    public static ResolvedStyle empty() {
        return new ResolvedStyle(null, null, null, null, null, null, null, Collections.<String, String>emptyMap());
    }

    /**
     * Returns the foreground color, if set.
     */
    public Optional<Color> foreground() {
        return Optional.ofNullable(foreground);
    }

    /**
     * Returns the background color, if set.
     */
    public Optional<Color> background() {
        return Optional.ofNullable(background);
    }

    /**
     * Returns the text modifiers.
     */
    public Set<Modifier> modifiers() {
        return modifiers;
    }

    /**
     * Returns the padding, if set.
     */
    public Optional<Padding> padding() {
        return Optional.ofNullable(padding);
    }

    /**
     * Returns the text alignment, if set.
     */
    public Optional<Alignment> alignment() {
        return Optional.ofNullable(alignment);
    }

    /**
     * Returns the border type, if set.
     */
    public Optional<BorderType> borderType() {
        return Optional.ofNullable(borderType);
    }

    /**
     * Returns the width behavior.
     * Defaults to {@link Width#FILL} if not explicitly set.
     */
    public Width width() {
        return width != null ? width : Width.FILL;
    }

    /**
     * Returns additional properties not mapped to specific fields.
     */
    public Map<String, String> additionalProperties() {
        return additionalProperties;
    }

    /**
     * Gets an additional property value.
     */
    public Optional<String> getProperty(String name) {
        return Optional.ofNullable(additionalProperties.get(name));
    }

    /**
     * Converts this resolved style to a TamboUI Style object.
     * <p>
     * The {@link Width} property is stored as a Style extension and can be
     * retrieved via {@code style.extension(Width.class, Width.FILL)}.
     *
     * @return the Style object
     */
    public Style toStyle() {
        Style style = Style.EMPTY;

        if (foreground != null) {
            style = style.fg(foreground);
        }
        if (background != null) {
            style = style.bg(background);
        }
        for (Modifier modifier : modifiers) {
            style = style.addModifier(modifier);
        }
        if (width != null) {
            style = style.withExtension(Width.class, width);
        }

        return style;
    }

    /**
     * Returns true if this style has any properties set.
     */
    public boolean hasProperties() {
        return foreground != null || background != null ||
                !modifiers.isEmpty() || padding != null || alignment != null ||
                borderType != null || width != null || !additionalProperties.isEmpty();
    }

    /**
     * Creates a new builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for ResolvedStyle.
     */
    public static final class Builder {
        private Color foreground;
        private Color background;
        private Set<Modifier> modifiers = EnumSet.noneOf(Modifier.class);
        private Padding padding;
        private Alignment alignment;
        private BorderType borderType;
        private Width width;
        private Map<String, String> additionalProperties = new HashMap<>();

        private Builder() {
        }

        public Builder foreground(Color color) {
            this.foreground = color;
            return this;
        }

        public Builder background(Color color) {
            this.background = color;
            return this;
        }

        public Builder addModifier(Modifier modifier) {
            this.modifiers.add(modifier);
            return this;
        }

        public Builder addModifiers(Set<Modifier> modifiers) {
            this.modifiers.addAll(modifiers);
            return this;
        }

        public Builder padding(Padding padding) {
            this.padding = padding;
            return this;
        }

        public Builder alignment(Alignment alignment) {
            this.alignment = alignment;
            return this;
        }

        public Builder borderType(BorderType borderType) {
            this.borderType = borderType;
            return this;
        }

        public Builder width(Width width) {
            this.width = width;
            return this;
        }

        public Builder property(String name, String value) {
            this.additionalProperties.put(name, value);
            return this;
        }

        public ResolvedStyle build() {
            return new ResolvedStyle(foreground, background, modifiers, padding, alignment, borderType, width, additionalProperties);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ResolvedStyle{");
        if (foreground != null) {
            sb.append("fg=").append(foreground).append(", ");
        }
        if (background != null) {
            sb.append("bg=").append(background).append(", ");
        }
        if (!modifiers.isEmpty()) {
            sb.append("modifiers=").append(modifiers).append(", ");
        }
        if (padding != null) {
            sb.append("padding=").append(padding).append(", ");
        }
        if (alignment != null) {
            sb.append("alignment=").append(alignment).append(", ");
        }
        if (borderType != null) {
            sb.append("borderType=").append(borderType).append(", ");
        }
        if (!additionalProperties.isEmpty()) {
            sb.append("properties=").append(additionalProperties);
        }
        sb.append("}");
        return sb.toString();
    }
}

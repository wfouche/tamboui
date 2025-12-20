/*
 * Copyright (c) 2025 JRatatui Contributors
 * SPDX-License-Identifier: MIT
 */
package io.github.jratatui.tui;

import java.time.Duration;

/**
 * Configuration options for {@link TuiRunner}.
 *
 * @param rawMode          whether to enable raw mode (default: true)
 * @param alternateScreen  whether to use alternate screen buffer (default: true)
 * @param hideCursor       whether to hide the cursor (default: true)
 * @param mouseCapture     whether to capture mouse events (default: false)
 * @param pollTimeout      timeout for polling events (default: 100ms)
 * @param tickRate         interval between tick events, or null to disable (default: null)
 * @param shutdownHook     whether to register a shutdown hook for terminal cleanup (default: true)
 */
public record TuiConfig(
    boolean rawMode,
    boolean alternateScreen,
    boolean hideCursor,
    boolean mouseCapture,
    Duration pollTimeout,
    Duration tickRate,
    boolean shutdownHook
) {

    /**
     * Returns the default configuration.
     */
    public static TuiConfig defaults() {
        return new TuiConfig(
            true,                        // rawMode
            true,                        // alternateScreen
            true,                        // hideCursor
            false,                       // mouseCapture
            Duration.ofMillis(100),      // pollTimeout
            null,                        // tickRate (disabled)
            true                         // shutdownHook
        );
    }

    /**
     * Returns a configuration suitable for animated applications.
     *
     * @param tickRate the interval between tick events (e.g., Duration.ofMillis(16) for ~60fps)
     */
    public static TuiConfig withAnimation(Duration tickRate) {
        return builder().tickRate(tickRate).build();
    }

    /**
     * Returns a new builder with default values.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns true if tick events are enabled.
     */
    public boolean ticksEnabled() {
        return tickRate != null;
    }

    /**
     * Builder for {@link TuiConfig}.
     */
    public static final class Builder {
        private boolean rawMode = true;
        private boolean alternateScreen = true;
        private boolean hideCursor = true;
        private boolean mouseCapture = false;
        private Duration pollTimeout = Duration.ofMillis(100);
        private Duration tickRate = null;
        private boolean shutdownHook = true;

        private Builder() {}

        /**
         * Sets whether to enable raw mode.
         */
        public Builder rawMode(boolean rawMode) {
            this.rawMode = rawMode;
            return this;
        }

        /**
         * Sets whether to use alternate screen buffer.
         */
        public Builder alternateScreen(boolean alternateScreen) {
            this.alternateScreen = alternateScreen;
            return this;
        }

        /**
         * Sets whether to hide the cursor.
         */
        public Builder hideCursor(boolean hideCursor) {
            this.hideCursor = hideCursor;
            return this;
        }

        /**
         * Sets whether to capture mouse events.
         */
        public Builder mouseCapture(boolean mouseCapture) {
            this.mouseCapture = mouseCapture;
            return this;
        }

        /**
         * Sets the timeout for polling events.
         */
        public Builder pollTimeout(Duration pollTimeout) {
            this.pollTimeout = pollTimeout != null ? pollTimeout : Duration.ofMillis(100);
            return this;
        }

        /**
         * Sets the interval between tick events.
         * Set to null to disable tick events.
         *
         * @param tickRate the tick interval (e.g., Duration.ofMillis(16) for ~60fps)
         */
        public Builder tickRate(Duration tickRate) {
            this.tickRate = tickRate;
            return this;
        }

        /**
         * Sets whether to register a shutdown hook for terminal cleanup.
         * When enabled (default), the terminal state will be restored even if
         * the application is terminated with Ctrl+C or other signals.
         *
         * @param shutdownHook true to enable shutdown hook (default), false to disable
         */
        public Builder shutdownHook(boolean shutdownHook) {
            this.shutdownHook = shutdownHook;
            return this;
        }

        /**
         * Builds the configuration.
         */
        public TuiConfig build() {
            return new TuiConfig(
                rawMode,
                alternateScreen,
                hideCursor,
                mouseCapture,
                pollTimeout,
                tickRate,
                shutdownHook
            );
        }
    }
}

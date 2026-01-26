/*
 * Copyright TamboUI Contributors
 * SPDX-License-Identifier: MIT
 */
package dev.tamboui.layout.columns;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ColumnOrder#resolveIndex(int, int, int, int)}.
 */
class ColumnOrderTest {

    @Nested
    @DisplayName("ROW_FIRST ordering")
    class RowFirst {

        @Test
        @DisplayName("maps (0,0) to index 0 in 2x2 grid")
        void firstCell() {
            assertThat(ColumnOrder.ROW_FIRST.resolveIndex(0, 0, 2, 2)).isEqualTo(0);
        }

        @Test
        @DisplayName("maps (0,1) to index 1 in 2x2 grid")
        void secondColumn() {
            assertThat(ColumnOrder.ROW_FIRST.resolveIndex(0, 1, 2, 2)).isEqualTo(1);
        }

        @Test
        @DisplayName("maps (1,0) to index 2 in 2x2 grid")
        void secondRow() {
            assertThat(ColumnOrder.ROW_FIRST.resolveIndex(1, 0, 2, 2)).isEqualTo(2);
        }

        @Test
        @DisplayName("maps (1,1) to index 3 in 2x2 grid")
        void lastCell() {
            assertThat(ColumnOrder.ROW_FIRST.resolveIndex(1, 1, 2, 2)).isEqualTo(3);
        }

        @Test
        @DisplayName("fills left-to-right in 3x3 grid")
        void threeByThree() {
            // Row 0: 0, 1, 2
            // Row 1: 3, 4, 5
            // Row 2: 6, 7, 8
            assertThat(ColumnOrder.ROW_FIRST.resolveIndex(0, 0, 3, 3)).isEqualTo(0);
            assertThat(ColumnOrder.ROW_FIRST.resolveIndex(0, 2, 3, 3)).isEqualTo(2);
            assertThat(ColumnOrder.ROW_FIRST.resolveIndex(1, 0, 3, 3)).isEqualTo(3);
            assertThat(ColumnOrder.ROW_FIRST.resolveIndex(2, 2, 3, 3)).isEqualTo(8);
        }
    }

    @Nested
    @DisplayName("COLUMN_FIRST ordering")
    class ColumnFirst {

        @Test
        @DisplayName("maps (0,0) to index 0 in 2x2 grid")
        void firstCell() {
            assertThat(ColumnOrder.COLUMN_FIRST.resolveIndex(0, 0, 2, 2)).isEqualTo(0);
        }

        @Test
        @DisplayName("maps (1,0) to index 1 in 2x2 grid (fills top-to-bottom)")
        void secondRowFirstColumn() {
            assertThat(ColumnOrder.COLUMN_FIRST.resolveIndex(1, 0, 2, 2)).isEqualTo(1);
        }

        @Test
        @DisplayName("maps (0,1) to index 2 in 2x2 grid (moves to next column)")
        void firstRowSecondColumn() {
            assertThat(ColumnOrder.COLUMN_FIRST.resolveIndex(0, 1, 2, 2)).isEqualTo(2);
        }

        @Test
        @DisplayName("maps (1,1) to index 3 in 2x2 grid")
        void lastCell() {
            assertThat(ColumnOrder.COLUMN_FIRST.resolveIndex(1, 1, 2, 2)).isEqualTo(3);
        }

        @Test
        @DisplayName("fills top-to-bottom in 3x2 grid")
        void threeRowsTwoColumns() {
            // Col 0: 0, 1, 2
            // Col 1: 3, 4, 5
            assertThat(ColumnOrder.COLUMN_FIRST.resolveIndex(0, 0, 3, 2)).isEqualTo(0);
            assertThat(ColumnOrder.COLUMN_FIRST.resolveIndex(1, 0, 3, 2)).isEqualTo(1);
            assertThat(ColumnOrder.COLUMN_FIRST.resolveIndex(2, 0, 3, 2)).isEqualTo(2);
            assertThat(ColumnOrder.COLUMN_FIRST.resolveIndex(0, 1, 3, 2)).isEqualTo(3);
            assertThat(ColumnOrder.COLUMN_FIRST.resolveIndex(1, 1, 3, 2)).isEqualTo(4);
            assertThat(ColumnOrder.COLUMN_FIRST.resolveIndex(2, 1, 3, 2)).isEqualTo(5);
        }
    }
}

/*
 * Copyright TamboUI Contributors
 * SPDX-License-Identifier: MIT
 */
package dev.tamboui.widgets.grid;

import dev.tamboui.buffer.Buffer;
import dev.tamboui.layout.Constraint;
import dev.tamboui.layout.Flex;
import dev.tamboui.layout.Layout;
import dev.tamboui.layout.Rect;
import dev.tamboui.widget.Widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.tamboui.util.CollectionUtil.listCopyOf;

/**
 * A CSS Grid-inspired layout widget that arranges children into a grid
 * with explicit control over grid dimensions, per-column/per-row sizing
 * constraints, and gutter spacing.
 * <p>
 * Column and row constraints cycle when fewer constraints than grid dimensions
 * (matching Textual behavior).
 *
 * <pre>{@code
 * Grid grid = Grid.builder()
 *     .children(widget1, widget2, widget3, widget4)
 *     .columnCount(2)
 *     .horizontalGutter(1)
 *     .verticalGutter(1)
 *     .build();
 *
 * grid.render(area, buffer);
 * }</pre>
 */
public final class Grid implements Widget {

    private final List<Widget> children;
    private final int columnCount;
    private final int horizontalGutter;
    private final int verticalGutter;
    private final Flex flex;
    private final List<Constraint> columnConstraints;
    private final List<Constraint> rowConstraints;
    private final int[] rowHeights;

    private Grid(Builder builder) {
        this.children = listCopyOf(builder.children);
        this.columnCount = builder.columnCount;
        this.horizontalGutter = builder.horizontalGutter;
        this.verticalGutter = builder.verticalGutter;
        this.flex = builder.flex;
        this.columnConstraints = builder.columnConstraints != null
            ? listCopyOf(builder.columnConstraints) : null;
        this.rowConstraints = builder.rowConstraints != null
            ? listCopyOf(builder.rowConstraints) : null;
        this.rowHeights = builder.rowHeights != null ? builder.rowHeights.clone() : null;
    }

    /**
     * Creates a new builder.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void render(Rect area, Buffer buffer) {
        if (area.isEmpty() || children.isEmpty() || columnCount <= 0) {
            return;
        }

        int cols = Math.min(columnCount, children.size());
        int rows = (children.size() + cols - 1) / cols;

        // Build horizontal constraints with gutter gaps
        List<Constraint> hConstraints = buildHorizontalConstraints(cols);

        List<Rect> colAreas = Layout.horizontal()
            .constraints(hConstraints)
            .flex(flex)
            .split(area);

        // Extract only column areas (skip gutter areas)
        List<Rect> columnRects = extractColumnRects(colAreas);

        if (rowConstraints != null && !rowConstraints.isEmpty()) {
            renderWithRowConstraints(area, buffer, rows, cols, columnRects);
        } else if (rowHeights != null) {
            renderWithExplicitRowHeights(area, buffer, rows, cols, columnRects);
        } else {
            renderWithEqualRowHeights(area, buffer, rows, cols, columnRects);
        }
    }

    private void renderWithRowConstraints(Rect area, Buffer buffer, int rows, int cols,
                                          List<Rect> columnRects) {
        // Build row constraints: cycle rowConstraints over rows, interleave with vertical gutter
        List<Constraint> vConstraints = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            vConstraints.add(rowConstraints.get(r % rowConstraints.size()));
            if (verticalGutter > 0 && r < rows - 1) {
                vConstraints.add(Constraint.length(verticalGutter));
            }
        }

        List<Rect> allRowAreas = Layout.vertical()
            .constraints(vConstraints)
            .flex(flex)
            .split(area);

        // Extract only row areas (skip gutter areas)
        List<Rect> rowRects = new ArrayList<>();
        for (int i = 0; i < allRowAreas.size(); i++) {
            if (verticalGutter > 0 && i % 2 == 1) {
                continue;
            }
            rowRects.add(allRowAreas.get(i));
        }

        // Render children
        for (int row = 0; row < rows && row < rowRects.size(); row++) {
            Rect rowRect = rowRects.get(row);
            for (int col = 0; col < cols && col < columnRects.size(); col++) {
                int childIndex = row * cols + col;
                if (childIndex < children.size()) {
                    Rect colRect = columnRects.get(col);
                    Rect cellArea = new Rect(colRect.x(), rowRect.y(), colRect.width(), rowRect.height());
                    children.get(childIndex).render(cellArea, buffer);
                }
            }
        }
    }

    private void renderWithExplicitRowHeights(Rect area, Buffer buffer, int rows, int cols,
                                              List<Rect> columnRects) {
        int[] heights = computeRowHeights(rows, area.height());
        renderRows(area, buffer, rows, cols, columnRects, heights);
    }

    private void renderWithEqualRowHeights(Rect area, Buffer buffer, int rows, int cols,
                                           List<Rect> columnRects) {
        int availableHeight = area.height() - verticalGutter * (rows - 1);
        int baseHeight = Math.max(0, availableHeight) / rows;
        int remainder = Math.max(0, availableHeight) % rows;
        int[] heights = new int[rows];
        for (int i = 0; i < rows; i++) {
            heights[i] = baseHeight + (i < remainder ? 1 : 0);
        }
        renderRows(area, buffer, rows, cols, columnRects, heights);
    }

    private void renderRows(Rect area, Buffer buffer, int rows, int cols,
                            List<Rect> columnRects, int[] heights) {
        int currentY = area.y();
        for (int row = 0; row < rows; row++) {
            if (currentY >= area.bottom()) {
                break;
            }
            int rowHeight = Math.min(heights[row], area.bottom() - currentY);
            for (int col = 0; col < cols && col < columnRects.size(); col++) {
                int childIndex = row * cols + col;
                if (childIndex < children.size()) {
                    Rect colRect = columnRects.get(col);
                    Rect cellArea = new Rect(colRect.x(), currentY, colRect.width(), rowHeight);
                    children.get(childIndex).render(cellArea, buffer);
                }
            }
            currentY += rowHeight + verticalGutter;
        }
    }

    private List<Constraint> buildHorizontalConstraints(int cols) {
        List<Constraint> constraints = new ArrayList<>();
        for (int c = 0; c < cols; c++) {
            if (columnConstraints != null && !columnConstraints.isEmpty()) {
                constraints.add(columnConstraints.get(c % columnConstraints.size()));
            } else {
                constraints.add(Constraint.fill());
            }
            if (horizontalGutter > 0 && c < cols - 1) {
                constraints.add(Constraint.length(horizontalGutter));
            }
        }
        return constraints;
    }

    private List<Rect> extractColumnRects(List<Rect> colAreas) {
        List<Rect> columnRects = new ArrayList<>();
        for (int i = 0; i < colAreas.size(); i++) {
            if (horizontalGutter > 0 && i % 2 == 1) {
                continue;
            }
            columnRects.add(colAreas.get(i));
        }
        return columnRects;
    }

    private int[] computeRowHeights(int rows, int availableHeight) {
        int[] heights = new int[rows];
        int remaining = availableHeight - verticalGutter * (rows - 1);
        remaining = Math.max(0, remaining);
        for (int i = 0; i < rows; i++) {
            if (rowHeights != null && i < rowHeights.length) {
                heights[i] = Math.min(rowHeights[i], remaining);
            } else {
                heights[i] = Math.min(1, remaining);
            }
            remaining = Math.max(0, remaining - heights[i]);
        }
        return heights;
    }

    /**
     * Builder for {@link Grid}.
     */
    public static final class Builder {
        private final List<Widget> children = new ArrayList<>();
        private int columnCount = 1;
        private int horizontalGutter = 0;
        private int verticalGutter = 0;
        private Flex flex = Flex.START;
        private List<Constraint> columnConstraints;
        private List<Constraint> rowConstraints;
        private int[] rowHeights;

        private Builder() {
        }

        /**
         * Sets the children widgets.
         *
         * @param children the child widgets
         * @return this builder
         */
        public Builder children(Widget... children) {
            this.children.clear();
            this.children.addAll(Arrays.asList(children));
            return this;
        }

        /**
         * Sets the children widgets from a list.
         *
         * @param children the child widgets
         * @return this builder
         */
        public Builder children(List<Widget> children) {
            this.children.clear();
            this.children.addAll(children);
            return this;
        }

        /**
         * Sets the number of columns.
         *
         * @param count the column count (must be at least 1)
         * @return this builder
         */
        public Builder columnCount(int count) {
            this.columnCount = Math.max(1, count);
            return this;
        }

        /**
         * Sets the horizontal gutter between columns.
         *
         * @param gutter the horizontal gutter in cells
         * @return this builder
         */
        public Builder horizontalGutter(int gutter) {
            this.horizontalGutter = Math.max(0, gutter);
            return this;
        }

        /**
         * Sets the vertical gutter between rows.
         *
         * @param gutter the vertical gutter in cells
         * @return this builder
         */
        public Builder verticalGutter(int gutter) {
            this.verticalGutter = Math.max(0, gutter);
            return this;
        }

        /**
         * Sets how remaining space is distributed.
         *
         * @param flex the flex mode
         * @return this builder
         */
        public Builder flex(Flex flex) {
            this.flex = flex;
            return this;
        }

        /**
         * Sets the width constraints for columns.
         * <p>
         * Constraints cycle when fewer than the column count.
         *
         * @param constraints the column width constraints
         * @return this builder
         */
        public Builder columnConstraints(Constraint... constraints) {
            this.columnConstraints = Arrays.asList(constraints);
            return this;
        }

        /**
         * Sets the width constraints for columns from a list.
         *
         * @param constraints the column width constraints
         * @return this builder
         */
        public Builder columnConstraints(List<Constraint> constraints) {
            this.columnConstraints = new ArrayList<>(constraints);
            return this;
        }

        /**
         * Sets the height constraints for rows.
         * <p>
         * When set, row heights are determined by the layout solver
         * rather than by children preferred heights or equal distribution.
         * Constraints cycle when fewer than the row count.
         *
         * @param constraints the row height constraints
         * @return this builder
         */
        public Builder rowConstraints(Constraint... constraints) {
            this.rowConstraints = Arrays.asList(constraints);
            return this;
        }

        /**
         * Sets the height constraints for rows from a list.
         *
         * @param constraints the row height constraints
         * @return this builder
         */
        public Builder rowConstraints(List<Constraint> constraints) {
            this.rowConstraints = new ArrayList<>(constraints);
            return this;
        }

        /**
         * Sets explicit row heights.
         * <p>
         * If not set and no row constraints are set, rows share the available height equally.
         * If fewer heights than rows are provided, remaining rows default to 1.
         *
         * @param heights the row heights
         * @return this builder
         */
        public Builder rowHeights(int... heights) {
            this.rowHeights = heights.clone();
            return this;
        }

        /**
         * Builds the {@link Grid} widget.
         *
         * @return a new Grid widget
         */
        public Grid build() {
            return new Grid(this);
        }
    }
}

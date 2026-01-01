/*
 * Copyright (c) 2025 TamboUI Contributors
 * SPDX-License-Identifier: MIT
 */
package dev.tamboui.demo.filemanager;

import dev.tamboui.layout.Constraint;
import dev.tamboui.layout.Rect;
import dev.tamboui.style.Color;
import dev.tamboui.terminal.Frame;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;
import dev.tamboui.widgets.Clear;

import static dev.tamboui.toolkit.Toolkit.*;

/**
 * Main view implementing Element directly for proper event handling.
 * This follows the pattern used by FloatingPanelsArea in toolkit-demo.
 */
public class FileManagerView implements Element {

    private final FileManagerController manager;
    private final FileManagerKeyHandler keyHandler;

    public FileManagerView(FileManagerController manager) {
        this.manager = manager;
        this.keyHandler = new FileManagerKeyHandler(manager);
    }

    @Override
    public void render(Frame frame, Rect area, RenderContext context) {

        // Build and render the UI tree
        var ui = column(
            header(),
            browserRow(context),
            helpBar()
        );
        ui.render(frame, area, context);

        // Render dialog on top if present
        if (manager.hasDialog()) {
            renderDialog(frame, area, context);
        }
    }

    private void renderDialog(Frame frame, Rect area, RenderContext context) {
        var type = manager.currentDialog();
        var message = manager.dialogMessage();

        // Handle input dialogs separately
        if (type == FileManagerController.DialogType.MKDIR_INPUT) {
            renderInputDialog(frame, area, context, "New Directory", message);
            return;
        }
        if (type == FileManagerController.DialogType.GOTO_INPUT) {
            renderInputDialog(frame, area, context, "Go To Directory", message);
            return;
        }

        var titleColor = switch (type) {
            case ERROR -> Color.RED;
            case DELETE_CONFIRM -> Color.YELLOW;
            default -> Color.CYAN;
        };

        var title = switch (type) {
            case COPY_CONFIRM -> "Copy";
            case MOVE_CONFIRM -> "Move";
            case DELETE_CONFIRM -> "Delete";
            case ERROR -> "Error";
            default -> "";
        };

        var buttons = type == FileManagerController.DialogType.ERROR
            ? "[Enter] OK"
            : "[y] Yes  [n] No  [Esc] Cancel";

        // Center the dialog
        int dialogWidth = Math.max(40, message.length() + 4);
        int dialogHeight = 5;
        int x = (area.width() - dialogWidth) / 2;
        int y = (area.height() - dialogHeight) / 2;
        var dialogArea = new Rect(area.x() + x, area.y() + y, dialogWidth, dialogHeight);

        // Clear the dialog area first
        frame.renderWidget(Clear.INSTANCE, dialogArea);

        var dialog = panel(title,
            text(message),
            text(""),
            text(buttons).dim()
        ).rounded().borderColor(titleColor);

        dialog.render(frame, dialogArea, context);
    }

    private void renderInputDialog(Frame frame, Rect area, RenderContext context, String title, String prompt) {
        var input = manager.inputBuffer();
        var inputDisplay = input + "_";  // Show cursor

        int dialogWidth = Math.max(50, Math.max(prompt.length() + 4, inputDisplay.length() + 6));
        int dialogHeight = 6;
        int x = (area.width() - dialogWidth) / 2;
        int y = (area.height() - dialogHeight) / 2;
        var dialogArea = new Rect(area.x() + x, area.y() + y, dialogWidth, dialogHeight);

        // Clear the dialog area first
        frame.renderWidget(Clear.INSTANCE, dialogArea);

        var dialog = panel(title,
            text(prompt),
            text(""),
            text(inputDisplay).cyan(),
            text("[Enter] Confirm  [Esc] Cancel").dim()
        ).rounded().borderColor(Color.CYAN);

        dialog.render(frame, dialogArea, context);
    }

    @Override
    public Constraint constraint() {
        return Constraint.fill();
    }

    @Override
    public EventResult handleKeyEvent(KeyEvent event, boolean focused) {
        // Handle all keys globally since we're the root element
        return keyHandler.handle(event);
    }

    private Element header() {
        return row(
            text(" File Manager ").bold().cyan(),
            spacer(),
            text(" [Tab] Switch ").dim(),
            text(" [Space] Mark ").dim(),
            text(" [F5] Copy ").dim(),
            text(" [F6] Move ").dim(),
            text(" [F7] Mkdir ").dim(),
            text(" [F8] Delete ").dim(),
            text(" [o] Goto ").dim(),
            text(" [q] Quit ").dim()
        ).length(1);
    }

    private Element browserRow(RenderContext context) {
        var leftActive = manager.isActive(FileManagerController.Side.LEFT);
        var rightActive = manager.isActive(FileManagerController.Side.RIGHT);

        return row(
            browserPanel(manager.leftBrowser(), leftActive, "left", context),
            browserPanel(manager.rightBrowser(), rightActive, "right", context)
        ).fill();
    }

    private Element browserPanel(DirectoryBrowserController browser, boolean active, String id, RenderContext context) {
        return panel(browser.currentDirectory().toString(),
            fileList(browser, active)
        )
        .id(id)
        .rounded()
        .titleEllipsisStart()
        .borderColor(active ? Color.CYAN : Color.DARK_GRAY)
        .fill();
    }

    private Element fileList(DirectoryBrowserController browser, boolean active) {
        var entries = browser.entries();
        var offset = browser.scrollOffset();
        var cursor = browser.cursorIndex();
        var visibleCount = Math.min(20, Math.max(0, entries.size() - offset));

        if (visibleCount == 0) {
            return text("Empty").dim().fill();
        }

        var elements = new Element[visibleCount];
        for (int i = 0; i < visibleCount; i++) {
            var entryIndex = offset + i;
            var entry = entries.get(entryIndex);
            var isSelected = entryIndex == cursor;
            elements[i] = fileEntry(browser, entry, isSelected, active);
        }

        return column(elements).fill();
    }

    private Element fileEntry(DirectoryBrowserController browser, DirectoryBrowserController.FileEntry entry, boolean selected, boolean active) {
        var name = entry.name();
        var isMarked = browser.isMarked(name);
        var marker = isMarked ? "*" : " ";
        var displayName = entry.isDirectory() && !name.equals("..") ? name + "/" : name;
        var size = entry.isDirectory() ? "<DIR>" : formatSize(entry.size());

        // Combine marker + space + name into single left-aligned text
        var fullName = marker + " " + displayName;

        // Determine colors based on state
        Color fg, bg;
        if (selected && active) {
            fg = Color.BLACK;
            bg = Color.WHITE;
        } else if (selected) {
            fg = Color.WHITE;
            bg = Color.rgb(60, 60, 60);
        } else {
            fg = isMarked ? Color.YELLOW
                : entry.name().equals("..") ? Color.BLUE
                : entry.isDirectory() ? Color.CYAN
                : Color.WHITE;
            bg = null;
        }

        var nameText = text(fullName).fg(fg).ellipsis().fill();
        var sizeText = text(" " + size).fg(selected ? fg : Color.GRAY);

        if (bg != null) {
            return row(nameText.bg(bg), sizeText.bg(bg)).length(1);
        }
        return row(nameText, sizeText).length(1);
    }

    private Element helpBar() {
        var browser = manager.activeBrowser();
        var entry = browser.selectedEntry();

        String info;
        if (entry == null) {
            info = "Empty directory";
        } else if (entry.isDirectory()) {
            info = entry.name().equals("..") ? "Parent directory" : "Directory: " + entry.name();
        } else {
            info = String.format("%s (%s)", entry.name(), formatSize(entry.size()));
        }

        return row(
            text(info).fill(),
            text("[Enter] Open  [Backspace] Up  [+] Mark All  [-] Unmark").dim()
        ).length(1);
    }

    private static String formatSize(long size) {
        if (size < 1024) {
            return size + " B";
        }
        if (size < 1024 * 1024) {
            return String.format("%.1f K", size / 1024.0);
        }
        if (size < 1024 * 1024 * 1024) {
            return String.format("%.1f M", size / (1024.0 * 1024));
        }
        return String.format("%.1f G", size / (1024.0 * 1024 * 1024));
    }
}

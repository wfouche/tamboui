/*
 * Copyright (c) 2025 Glimt Contributors
 * SPDX-License-Identifier: MIT
 */
package ink.glimt.tui;

import ink.glimt.tui.event.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.time.Duration;

import static org.assertj.core.api.Assertions.*;

class KeysTest {

    @Nested
    @DisplayName("isQuit")
    class IsQuit {
        @Test
        @DisplayName("returns true for 'q'")
        void trueForLowercaseQ() {
            assertThat(Keys.isQuit(KeyEvent.ofChar('q'))).isTrue();
        }

        @Test
        @DisplayName("returns true for 'Q'")
        void trueForUppercaseQ() {
            assertThat(Keys.isQuit(KeyEvent.ofChar('Q'))).isTrue();
        }

        @Test
        @DisplayName("returns true for Ctrl+C")
        void trueForCtrlC() {
            assertThat(Keys.isQuit(KeyEvent.ofChar('c', KeyModifiers.CTRL))).isTrue();
        }

        @Test
        @DisplayName("returns false for other keys")
        void falseForOtherKeys() {
            assertThat(Keys.isQuit(KeyEvent.ofChar('x'))).isFalse();
            assertThat(Keys.isQuit(KeyEvent.ofKey(KeyCode.ESCAPE))).isFalse();
        }

        @Test
        @DisplayName("returns false for non-key events")
        void falseForNonKeyEvents() {
            assertThat(Keys.isQuit(ResizeEvent.of(80, 24))).isFalse();
            assertThat(Keys.isQuit(TickEvent.of(1, Duration.ZERO))).isFalse();
        }
    }

    @Nested
    @DisplayName("isUp")
    class IsUp {
        @Test
        @DisplayName("returns true for Up arrow")
        void trueForUpArrow() {
            assertThat(Keys.isUp(KeyEvent.ofKey(KeyCode.UP))).isTrue();
        }

        @Test
        @DisplayName("returns true for 'k' (vim)")
        void trueForK() {
            assertThat(Keys.isUp(KeyEvent.ofChar('k'))).isTrue();
            assertThat(Keys.isUp(KeyEvent.ofChar('K'))).isTrue();
        }

        @Test
        @DisplayName("returns false for other keys")
        void falseForOtherKeys() {
            assertThat(Keys.isUp(KeyEvent.ofKey(KeyCode.DOWN))).isFalse();
            assertThat(Keys.isUp(KeyEvent.ofChar('j'))).isFalse();
        }
    }

    @Nested
    @DisplayName("isDown")
    class IsDown {
        @Test
        @DisplayName("returns true for Down arrow")
        void trueForDownArrow() {
            assertThat(Keys.isDown(KeyEvent.ofKey(KeyCode.DOWN))).isTrue();
        }

        @Test
        @DisplayName("returns true for 'j' (vim)")
        void trueForJ() {
            assertThat(Keys.isDown(KeyEvent.ofChar('j'))).isTrue();
            assertThat(Keys.isDown(KeyEvent.ofChar('J'))).isTrue();
        }

        @Test
        @DisplayName("returns false for other keys")
        void falseForOtherKeys() {
            assertThat(Keys.isDown(KeyEvent.ofKey(KeyCode.UP))).isFalse();
            assertThat(Keys.isDown(KeyEvent.ofChar('k'))).isFalse();
        }
    }

    @Nested
    @DisplayName("isLeft")
    class IsLeft {
        @Test
        @DisplayName("returns true for Left arrow")
        void trueForLeftArrow() {
            assertThat(Keys.isLeft(KeyEvent.ofKey(KeyCode.LEFT))).isTrue();
        }

        @Test
        @DisplayName("returns true for 'h' (vim)")
        void trueForH() {
            assertThat(Keys.isLeft(KeyEvent.ofChar('h'))).isTrue();
            assertThat(Keys.isLeft(KeyEvent.ofChar('H'))).isTrue();
        }
    }

    @Nested
    @DisplayName("isRight")
    class IsRight {
        @Test
        @DisplayName("returns true for Right arrow")
        void trueForRightArrow() {
            assertThat(Keys.isRight(KeyEvent.ofKey(KeyCode.RIGHT))).isTrue();
        }

        @Test
        @DisplayName("returns true for 'l' (vim)")
        void trueForL() {
            assertThat(Keys.isRight(KeyEvent.ofChar('l'))).isTrue();
            assertThat(Keys.isRight(KeyEvent.ofChar('L'))).isTrue();
        }
    }

    @Nested
    @DisplayName("Arrow keys only")
    class ArrowKeysOnly {
        @Test
        @DisplayName("isArrowUp returns true only for Up arrow")
        void isArrowUp() {
            assertThat(Keys.isArrowUp(KeyEvent.ofKey(KeyCode.UP))).isTrue();
            assertThat(Keys.isArrowUp(KeyEvent.ofChar('k'))).isFalse();
        }

        @Test
        @DisplayName("isArrowDown returns true only for Down arrow")
        void isArrowDown() {
            assertThat(Keys.isArrowDown(KeyEvent.ofKey(KeyCode.DOWN))).isTrue();
            assertThat(Keys.isArrowDown(KeyEvent.ofChar('j'))).isFalse();
        }

        @Test
        @DisplayName("isArrowLeft returns true only for Left arrow")
        void isArrowLeft() {
            assertThat(Keys.isArrowLeft(KeyEvent.ofKey(KeyCode.LEFT))).isTrue();
            assertThat(Keys.isArrowLeft(KeyEvent.ofChar('h'))).isFalse();
        }

        @Test
        @DisplayName("isArrowRight returns true only for Right arrow")
        void isArrowRight() {
            assertThat(Keys.isArrowRight(KeyEvent.ofKey(KeyCode.RIGHT))).isTrue();
            assertThat(Keys.isArrowRight(KeyEvent.ofChar('l'))).isFalse();
        }
    }

    @Nested
    @DisplayName("Page navigation")
    class PageNavigation {
        @Test
        @DisplayName("isPageUp for PageUp and Ctrl+U")
        void isPageUp() {
            assertThat(Keys.isPageUp(KeyEvent.ofKey(KeyCode.PAGE_UP))).isTrue();
            assertThat(Keys.isPageUp(KeyEvent.ofChar('u', KeyModifiers.CTRL))).isTrue();
            assertThat(Keys.isPageUp(KeyEvent.ofChar('u'))).isFalse();
        }

        @Test
        @DisplayName("isPageDown for PageDown and Ctrl+D")
        void isPageDown() {
            assertThat(Keys.isPageDown(KeyEvent.ofKey(KeyCode.PAGE_DOWN))).isTrue();
            assertThat(Keys.isPageDown(KeyEvent.ofChar('d', KeyModifiers.CTRL))).isTrue();
            assertThat(Keys.isPageDown(KeyEvent.ofChar('d'))).isFalse();
        }

        @Test
        @DisplayName("isHome for Home and 'g'")
        void isHome() {
            assertThat(Keys.isHome(KeyEvent.ofKey(KeyCode.HOME))).isTrue();
            assertThat(Keys.isHome(KeyEvent.ofChar('g'))).isTrue();
        }

        @Test
        @DisplayName("isEnd for End and 'G'")
        void isEnd() {
            assertThat(Keys.isEnd(KeyEvent.ofKey(KeyCode.END))).isTrue();
            assertThat(Keys.isEnd(KeyEvent.ofChar('G'))).isTrue();
        }
    }

    @Nested
    @DisplayName("Selection and action")
    class SelectionAction {
        @Test
        @DisplayName("isSelect for Enter and Space")
        void isSelect() {
            assertThat(Keys.isSelect(KeyEvent.ofKey(KeyCode.ENTER))).isTrue();
            assertThat(Keys.isSelect(KeyEvent.ofChar(' '))).isTrue();
            assertThat(Keys.isSelect(KeyEvent.ofChar('x'))).isFalse();
        }

        @Test
        @DisplayName("isEnter for Enter only")
        void isEnter() {
            assertThat(Keys.isEnter(KeyEvent.ofKey(KeyCode.ENTER))).isTrue();
            assertThat(Keys.isEnter(KeyEvent.ofChar(' '))).isFalse();
        }

        @Test
        @DisplayName("isEscape for Escape key")
        void isEscape() {
            assertThat(Keys.isEscape(KeyEvent.ofKey(KeyCode.ESCAPE))).isTrue();
            assertThat(Keys.isEscape(KeyEvent.ofChar('q'))).isFalse();
        }

        @Test
        @DisplayName("isTab for Tab key")
        void isTab() {
            assertThat(Keys.isTab(KeyEvent.ofKey(KeyCode.TAB))).isTrue();
        }

        @Test
        @DisplayName("isBackTab for Shift+Tab")
        void isBackTab() {
            assertThat(Keys.isBackTab(KeyEvent.ofKey(KeyCode.TAB, KeyModifiers.SHIFT))).isTrue();
            assertThat(Keys.isBackTab(KeyEvent.ofKey(KeyCode.TAB))).isFalse();
        }

        @Test
        @DisplayName("isBackspace for Backspace key")
        void isBackspace() {
            assertThat(Keys.isBackspace(KeyEvent.ofKey(KeyCode.BACKSPACE))).isTrue();
        }

        @Test
        @DisplayName("isDelete for Delete key")
        void isDelete() {
            assertThat(Keys.isDelete(KeyEvent.ofKey(KeyCode.DELETE))).isTrue();
        }
    }

    @Nested
    @DisplayName("Character matching")
    class CharacterMatching {
        @Test
        @DisplayName("isChar matches specific character")
        void isChar() {
            assertThat(Keys.isChar(KeyEvent.ofChar('a'), 'a')).isTrue();
            assertThat(Keys.isChar(KeyEvent.ofChar('a'), 'b')).isFalse();
        }

        @Test
        @DisplayName("isAnyChar matches any of given characters")
        void isAnyChar() {
            assertThat(Keys.isAnyChar(KeyEvent.ofChar('y'), 'y', 'n')).isTrue();
            assertThat(Keys.isAnyChar(KeyEvent.ofChar('n'), 'y', 'n')).isTrue();
            assertThat(Keys.isAnyChar(KeyEvent.ofChar('x'), 'y', 'n')).isFalse();
        }
    }

    @Nested
    @DisplayName("Function keys")
    class FunctionKeys {
        @Test
        @DisplayName("isFunctionKey returns true for F1-F12")
        void isFunctionKey() {
            assertThat(Keys.isFunctionKey(KeyEvent.ofKey(KeyCode.F1))).isTrue();
            assertThat(Keys.isFunctionKey(KeyEvent.ofKey(KeyCode.F5))).isTrue();
            assertThat(Keys.isFunctionKey(KeyEvent.ofKey(KeyCode.F12))).isTrue();
            assertThat(Keys.isFunctionKey(KeyEvent.ofKey(KeyCode.ENTER))).isFalse();
            assertThat(Keys.isFunctionKey(KeyEvent.ofChar('f'))).isFalse();
        }

        @Test
        @DisplayName("functionKeyNumber returns correct number")
        void functionKeyNumber() {
            assertThat(Keys.functionKeyNumber(KeyEvent.ofKey(KeyCode.F1))).isEqualTo(1);
            assertThat(Keys.functionKeyNumber(KeyEvent.ofKey(KeyCode.F5))).isEqualTo(5);
            assertThat(Keys.functionKeyNumber(KeyEvent.ofKey(KeyCode.F12))).isEqualTo(12);
            assertThat(Keys.functionKeyNumber(KeyEvent.ofKey(KeyCode.ENTER))).isEqualTo(-1);
            assertThat(Keys.functionKeyNumber(KeyEvent.ofChar('f'))).isEqualTo(-1);
        }
    }
}

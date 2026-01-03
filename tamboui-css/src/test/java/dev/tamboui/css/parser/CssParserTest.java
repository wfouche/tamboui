/*
 * Copyright (c) 2025 TamboUI Contributors
 * SPDX-License-Identifier: MIT
 */
package dev.tamboui.css.parser;

import dev.tamboui.css.model.PropertyValue;
import dev.tamboui.css.model.Rule;
import dev.tamboui.css.model.Stylesheet;
import dev.tamboui.css.selector.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CssParserTest {

    @Test
    void parsesEmptyStylesheet() {
        Stylesheet stylesheet = CssParser.parse("");

        assertThat(stylesheet.rules()).isEmpty();
        assertThat(stylesheet.variables()).isEmpty();
    }

    @Test
    void parsesVariable() {
        Stylesheet stylesheet = CssParser.parse("$primary: #3498db;");

        assertThat(stylesheet.variables()).containsEntry("primary", "#3498db");
        assertThat(stylesheet.rules()).isEmpty();
    }

    @Test
    void parsesMultipleVariables() {
        String css = "$primary: blue;\n$error: red;";
        Stylesheet stylesheet = CssParser.parse(css);

        assertThat(stylesheet.variables())
                .containsEntry("primary", "blue")
                .containsEntry("error", "red");
    }

    @Test
    void parsesTypeSelector() {
        Stylesheet stylesheet = CssParser.parse("Panel { color: red; }");

        assertThat(stylesheet.rules()).hasSize(1);
        Rule rule = stylesheet.rules().get(0);
        assertThat(rule.selector()).isInstanceOf(TypeSelector.class);
        assertThat(((TypeSelector) rule.selector()).typeName()).isEqualTo("Panel");
    }

    @Test
    void parsesIdSelector() {
        Stylesheet stylesheet = CssParser.parse("#sidebar { color: blue; }");

        assertThat(stylesheet.rules()).hasSize(1);
        Rule rule = stylesheet.rules().get(0);
        assertThat(rule.selector()).isInstanceOf(IdSelector.class);
        assertThat(((IdSelector) rule.selector()).id()).isEqualTo("sidebar");
    }

    @Test
    void parsesClassSelector() {
        Stylesheet stylesheet = CssParser.parse(".error { color: red; }");

        assertThat(stylesheet.rules()).hasSize(1);
        Rule rule = stylesheet.rules().get(0);
        assertThat(rule.selector()).isInstanceOf(ClassSelector.class);
        assertThat(((ClassSelector) rule.selector()).className()).isEqualTo("error");
    }

    @Test
    void parsesUniversalSelector() {
        Stylesheet stylesheet = CssParser.parse("* { margin: 0; }");

        assertThat(stylesheet.rules()).hasSize(1);
        Rule rule = stylesheet.rules().get(0);
        assertThat(rule.selector()).isEqualTo(UniversalSelector.INSTANCE);
    }

    @Test
    void parsesPseudoClassSelector() {
        Stylesheet stylesheet = CssParser.parse("Panel:focus { color: yellow; }");

        assertThat(stylesheet.rules()).hasSize(1);
        Rule rule = stylesheet.rules().get(0);
        assertThat(rule.selector()).isInstanceOf(CompoundSelector.class);
        CompoundSelector compound = (CompoundSelector) rule.selector();
        assertThat(compound.parts()).hasSize(2);
        assertThat(compound.parts().get(1)).isInstanceOf(PseudoClassSelector.class);
    }

    @Test
    void parsesCompoundSelector() {
        Stylesheet stylesheet = CssParser.parse("Panel.primary { color: blue; }");

        assertThat(stylesheet.rules()).hasSize(1);
        Rule rule = stylesheet.rules().get(0);
        assertThat(rule.selector()).isInstanceOf(CompoundSelector.class);
        CompoundSelector compound = (CompoundSelector) rule.selector();
        assertThat(compound.parts()).hasSize(2);
        assertThat(compound.parts().get(0)).isInstanceOf(TypeSelector.class);
        assertThat(compound.parts().get(1)).isInstanceOf(ClassSelector.class);
    }

    @Test
    void parsesDescendantSelector() {
        Stylesheet stylesheet = CssParser.parse("Panel Button { color: white; }");

        assertThat(stylesheet.rules()).hasSize(1);
        Rule rule = stylesheet.rules().get(0);
        assertThat(rule.selector()).isInstanceOf(DescendantSelector.class);
        DescendantSelector desc = (DescendantSelector) rule.selector();
        assertThat(desc.ancestor()).isInstanceOf(TypeSelector.class);
        assertThat(desc.descendant()).isInstanceOf(TypeSelector.class);
    }

    @Test
    void parsesChildSelector() {
        Stylesheet stylesheet = CssParser.parse("Panel > Button { color: gray; }");

        assertThat(stylesheet.rules()).hasSize(1);
        Rule rule = stylesheet.rules().get(0);
        assertThat(rule.selector()).isInstanceOf(ChildSelector.class);
        ChildSelector child = (ChildSelector) rule.selector();
        assertThat(child.parent()).isInstanceOf(TypeSelector.class);
        assertThat(child.child()).isInstanceOf(TypeSelector.class);
    }

    @Test
    void parsesDeclarations() {
        Stylesheet stylesheet = CssParser.parse("Panel { color: red; background: blue; }");

        Rule rule = stylesheet.rules().get(0);
        assertThat(rule.declarations()).hasSize(2);
        assertThat(rule.declarations().get("color").raw()).isEqualTo("red");
        assertThat(rule.declarations().get("background").raw()).isEqualTo("blue");
    }

    @Test
    void parsesImportantDeclaration() {
        Stylesheet stylesheet = CssParser.parse("Panel { color: red !important; }");

        Rule rule = stylesheet.rules().get(0);
        PropertyValue color = rule.declarations().get("color");
        assertThat(color.raw()).isEqualTo("red");
        assertThat(color.important()).isTrue();
    }

    @Test
    void parsesVariableReference() {
        String css = "$primary: blue;\nPanel { color: $primary; }";
        Stylesheet stylesheet = CssParser.parse(css);

        assertThat(stylesheet.variables()).containsEntry("primary", "blue");
        Rule rule = stylesheet.rules().get(0);
        assertThat(rule.declarations().get("color").raw()).isEqualTo("$primary");
    }

    @Test
    void parsesNestedRuleWithAmpersand() {
        String css = "Panel { color: red; &:focus { color: yellow; } }";
        Stylesheet stylesheet = CssParser.parse(css);

        assertThat(stylesheet.rules()).hasSize(2);
        // First rule: Panel { color: red }
        Rule rule1 = stylesheet.rules().get(0);
        assertThat(rule1.selector()).isInstanceOf(TypeSelector.class);
        assertThat(rule1.declarations()).containsKey("color");

        // Second rule: Panel:focus { color: yellow }
        Rule rule2 = stylesheet.rules().get(1);
        assertThat(rule2.selector()).isInstanceOf(CompoundSelector.class);
    }

    @Test
    void parsesComplexValue() {
        Stylesheet stylesheet = CssParser.parse("Panel { border: 1 2 3 4; }");

        Rule rule = stylesheet.rules().get(0);
        assertThat(rule.declarations().get("border").raw()).isEqualTo("1 2 3 4");
    }

    @Test
    void parsesRgbFunction() {
        Stylesheet stylesheet = CssParser.parse("Panel { color: rgb(255, 0, 0); }");

        Rule rule = stylesheet.rules().get(0);
        assertThat(rule.declarations().get("color").raw()).isEqualTo("rgb(255, 0, 0)");
    }

    @Test
    void parsesMultipleRules() {
        String css = "Panel { color: red; }\nButton { color: blue; }";
        Stylesheet stylesheet = CssParser.parse(css);

        assertThat(stylesheet.rules()).hasSize(2);
    }

    @Test
    void throwsOnMissingOpenBrace() {
        assertThatThrownBy(() -> CssParser.parse("Panel color: red; }"))
                .isInstanceOf(CssParseException.class)
                .hasMessageContaining("Expected '{'");
    }

    @Test
    void throwsOnMissingCloseBrace() {
        assertThatThrownBy(() -> CssParser.parse("Panel { color: red;"))
                .isInstanceOf(CssParseException.class)
                .hasMessageContaining("Expected '}'");
    }

    @Test
    void parsesDeclarationWithoutSemicolon() {
        // Last declaration before } doesn't require semicolon
        Stylesheet stylesheet = CssParser.parse("Panel { color: red }");

        Rule rule = stylesheet.rules().get(0);
        assertThat(rule.declarations().get("color").raw()).isEqualTo("red");
    }

    @Test
    void handlesComments() {
        String css = "/* Header styles */\nPanel { /* text color */ color: red; }";
        Stylesheet stylesheet = CssParser.parse(css);

        assertThat(stylesheet.rules()).hasSize(1);
        assertThat(stylesheet.rules().get(0).declarations()).containsKey("color");
    }

    @Test
    void preservesSourceOrder() {
        String css = "Panel { color: red; }\nButton { color: blue; }\nLabel { color: green; }";
        Stylesheet stylesheet = CssParser.parse(css);

        assertThat(stylesheet.rules()).hasSize(3);
        assertThat(stylesheet.rules().get(0).sourceOrder()).isEqualTo(0);
        assertThat(stylesheet.rules().get(1).sourceOrder()).isEqualTo(1);
        assertThat(stylesheet.rules().get(2).sourceOrder()).isEqualTo(2);
    }

    @Test
    void parsesFunctionalPseudoClassNthChildEven() {
        Stylesheet stylesheet = CssParser.parse("ListItem:nth-child(even) { background: gray; }");

        assertThat(stylesheet.rules()).hasSize(1);
        Rule rule = stylesheet.rules().get(0);
        assertThat(rule.selector()).isInstanceOf(CompoundSelector.class);
        CompoundSelector compound = (CompoundSelector) rule.selector();
        assertThat(compound.parts()).hasSize(2);
        assertThat(compound.parts().get(0)).isInstanceOf(TypeSelector.class);
        assertThat(compound.parts().get(1)).isInstanceOf(PseudoClassSelector.class);
        PseudoClassSelector pseudo = (PseudoClassSelector) compound.parts().get(1);
        assertThat(pseudo.pseudoClass()).isEqualTo("nth-child(even)");
    }

    @Test
    void parsesFunctionalPseudoClassNthChildOdd() {
        Stylesheet stylesheet = CssParser.parse("ListItem:nth-child(odd) { background: white; }");

        assertThat(stylesheet.rules()).hasSize(1);
        Rule rule = stylesheet.rules().get(0);
        CompoundSelector compound = (CompoundSelector) rule.selector();
        PseudoClassSelector pseudo = (PseudoClassSelector) compound.parts().get(1);
        assertThat(pseudo.pseudoClass()).isEqualTo("nth-child(odd)");
    }

    @Test
    void parsesFunctionalPseudoClassStandalone() {
        Stylesheet stylesheet = CssParser.parse(":nth-child(even) { color: red; }");

        assertThat(stylesheet.rules()).hasSize(1);
        Rule rule = stylesheet.rules().get(0);
        assertThat(rule.selector()).isInstanceOf(PseudoClassSelector.class);
        PseudoClassSelector pseudo = (PseudoClassSelector) rule.selector();
        assertThat(pseudo.pseudoClass()).isEqualTo("nth-child(even)");
    }

    @Test
    void parsesFunctionalPseudoClassWithNumericArgument() {
        Stylesheet stylesheet = CssParser.parse("ListItem:nth-child(3) { color: blue; }");

        assertThat(stylesheet.rules()).hasSize(1);
        Rule rule = stylesheet.rules().get(0);
        CompoundSelector compound = (CompoundSelector) rule.selector();
        PseudoClassSelector pseudo = (PseudoClassSelector) compound.parts().get(1);
        assertThat(pseudo.pseudoClass()).isEqualTo("nth-child(3)");
    }
}

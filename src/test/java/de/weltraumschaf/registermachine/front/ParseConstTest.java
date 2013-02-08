/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */

package de.weltraumschaf.registermachine.front;

import de.weltraumschaf.registermachine.inter.AstNode;
import de.weltraumschaf.registermachine.inter.ConstNode;
import de.weltraumschaf.registermachine.inter.FunctionNode;
import de.weltraumschaf.registermachine.inter.Type;
import de.weltraumschaf.registermachine.inter.Value;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ParseConstTest {

    // CHECKSTYLE:OFF
    @Rule public ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON

    @Test
    public void parseSingleConstantWithoutAssignment() {
        final Parser sut = Parser.forString("const foo\n");
        sut.parse();

        final FunctionNode main = sut.getAbstractSyntaxtTree();
        assertThat(main.getType(), is(AstNode.Type.FUNCTION));
        assertThat(main.getConstants().size(), is(1));

        final ConstNode foo = main.getConstants().get(0);
        assertThat(foo.getType(), is(AstNode.Type.CONST));
        assertThat(foo.getName(), is("foo"));
        assertThat(foo.getValue(), is(Value.getNil()));
    }

    @Test
    public void parseSingleConstantWithAssignment() {
        final Parser sut = Parser.forString("const foo = 3.14\n");
        sut.parse();

        final FunctionNode main = sut.getAbstractSyntaxtTree();
        assertThat(main.getType(), is(AstNode.Type.FUNCTION));
        assertThat(main.getConstants().size(), is(1));

        final ConstNode foo = main.getConstants().get(0);
        assertThat(foo.getType(), is(AstNode.Type.CONST));
        assertThat(foo.getName(), is("foo"));
        assertThat(foo.getValue(), is(Value.valueOf(3.14f)));
        assertThat(foo.getValue().getType(), is(Type.FLOAT));
    }

    @Test
    public void parseMulticonstWithoutAssignemtnWithoutNewlinesThrowsSyntaxException() {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Missing new line after {!");
        final Parser sut = Parser.forString("const { foo bar baz }");
        sut.parse();
    }

    @Test
    public void parseMultiConstantWithoutAssignemtnWithNewlines() {
        final Parser sut = Parser.forString("const {\n"
                + "  foo\n"
                + "  bar\n"
                + "  baz\n"
                + "}");
        sut.parse();

        final FunctionNode main = sut.getAbstractSyntaxtTree();
        assertThat(main.getType(), is(AstNode.Type.FUNCTION));
        assertThat(main.getConstants().size(), is(3));

        final ConstNode foo = main.getConstants().get(0);
        assertThat(foo.getType(), is(AstNode.Type.CONST));
        assertThat(foo.getName(), is("foo"));
        assertThat(foo.getValue(), is(Value.getNil()));
        assertThat(foo.getValue().getType(), is(Type.NIL));

        final ConstNode bar = main.getConstants().get(1);
        assertThat(bar.getType(), is(AstNode.Type.CONST));
        assertThat(bar.getName(), is("bar"));
        assertThat(bar.getValue(), is(Value.getNil()));
        assertThat(bar.getValue().getType(), is(Type.NIL));

        final ConstNode baz = main.getConstants().get(2);
        assertThat(baz.getType(), is(AstNode.Type.CONST));
        assertThat(baz.getName(), is("baz"));
        assertThat(baz.getValue(), is(Value.getNil()));
        assertThat(baz.getValue().getType(), is(Type.NIL));
    }

    @Test
    public void parseMultiConstantWithAssignemtnWithNewlines() {
        final Parser sut = Parser.forString("const {\n"
                + "  foo = 3.14\n"
                + "  bar = \"snafu\"\n"
                + "  baz = false\n"
                + "}");
        sut.parse();

        final FunctionNode main = sut.getAbstractSyntaxtTree();
        assertThat(main.getType(), is(AstNode.Type.FUNCTION));
        assertThat(main.getConstants().size(), is(3));

        final ConstNode foo = main.getConstants().get(0);
        assertThat(foo.getType(), is(AstNode.Type.CONST));
        assertThat(foo.getName(), is("foo"));
        assertThat(foo.getValue(), is(Value.valueOf(3.14f)));
        assertThat(foo.getValue().getType(), is(Type.FLOAT));

        final ConstNode bar = main.getConstants().get(1);
        assertThat(bar.getType(), is(AstNode.Type.CONST));
        assertThat(bar.getName(), is("bar"));
        assertThat(bar.getValue(), is(Value.valueOf("snafu")));
        assertThat(bar.getValue().getType(), is(Type.STRING));

        final ConstNode baz = main.getConstants().get(2);
        assertThat(baz.getType(), is(AstNode.Type.CONST));
        assertThat(baz.getName(), is("baz"));
        assertThat(baz.getValue(), is(Value.getFalse()));
        assertThat(baz.getValue().getType(), is(Type.BOOLEAN));
    }

    @Test
    public void parseMultiConstantWithAndWithoutAssignemtn() {
        final Parser sut = Parser.forString("const {\n"
                + "  foo = 3.14\n"
                + "  bar\n"
                + "  baz = false\n"
                + "}");
        sut.parse();

        final FunctionNode main = sut.getAbstractSyntaxtTree();
        assertThat(main.getType(), is(AstNode.Type.FUNCTION));
        assertThat(main.getConstants().size(), is(3));

        final ConstNode foo = main.getConstants().get(0);
        assertThat(foo.getType(), is(AstNode.Type.CONST));
        assertThat(foo.getName(), is("foo"));
        assertThat(foo.getValue(), is(Value.valueOf(3.14f)));
        assertThat(foo.getValue().getType(), is(Type.FLOAT));

        final ConstNode bar = main.getConstants().get(1);
        assertThat(bar.getType(), is(AstNode.Type.CONST));
        assertThat(bar.getName(), is("bar"));
        assertThat(bar.getValue(), is(Value.getNil()));
        assertThat(bar.getValue().getType(), is(Type.NIL));

        final ConstNode baz = main.getConstants().get(2);
        assertThat(baz.getType(), is(AstNode.Type.CONST));
        assertThat(baz.getName(), is("baz"));
        assertThat(baz.getValue(), is(Value.getFalse()));
        assertThat(baz.getValue().getType(), is(Type.BOOLEAN));
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.weltraumschaf.registermachine.front;

import de.weltraumschaf.registermachine.inter.AstNode;
import de.weltraumschaf.registermachine.inter.ConstNode;
import de.weltraumschaf.registermachine.inter.FunctionNode;
import de.weltraumschaf.registermachine.inter.Type;
import de.weltraumschaf.registermachine.inter.Value;
import de.weltraumschaf.registermachine.inter.VarNode;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
public class ParserTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseEmptySource() {
        final Parser sut = Parser.forString("");
        sut.parse();

        final FunctionNode main = sut.getAbstractSyntaxtTree();
        assertThat(main.getType(), is(AstNode.Type.FUNCTION));
        assertThat(main.getVariables().size(), is(0));
    }

    @Test
    public void parseSingleVaribaleWithoutAssignment() {
        final Parser sut = Parser.forString("var foo\n");
        sut.parse();

        final FunctionNode main = sut.getAbstractSyntaxtTree();
        assertThat(main.getType(), is(AstNode.Type.FUNCTION));
        assertThat(main.getVariables().size(), is(1));

        final VarNode foo = main.getVariables().get(0);
        assertThat(foo.getType(), is(AstNode.Type.VAR));
        assertThat(foo.getName(), is("foo"));
        assertThat(foo.getValue(), is(Value.getNil()));
    }

    @Test
    public void parseSingleVaribaleWithAssignment() {
        final Parser sut = Parser.forString("var foo = 3.14\n");
        sut.parse();

        final FunctionNode main = sut.getAbstractSyntaxtTree();
        assertThat(main.getType(), is(AstNode.Type.FUNCTION));
        assertThat(main.getVariables().size(), is(1));

        final VarNode foo = main.getVariables().get(0);
        assertThat(foo.getType(), is(AstNode.Type.VAR));
        assertThat(foo.getName(), is("foo"));
        assertThat(foo.getValue(), is(Value.valueOf(3.14f)));
        assertThat(foo.getValue().getType(), is(Type.FLOAT));
    }

    @Test
    public void parseMultiVaribaleWithoutAssignemtnWithoutNewlinesThrowsSyntaxException() {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Missing new line after {!");
        final Parser sut = Parser.forString("var { foo bar baz }");
        sut.parse();
    }

    @Test
    public void parseMultiVaribaleWithoutAssignemtnWithNewlines() {
        final Parser sut = Parser.forString("var {\n"
                + "  foo\n"
                + "  bar\n"
                + "  baz\n"
                + "}");
        sut.parse();

        final FunctionNode main = sut.getAbstractSyntaxtTree();
        assertThat(main.getType(), is(AstNode.Type.FUNCTION));
        assertThat(main.getVariables().size(), is(3));

        final VarNode foo = main.getVariables().get(0);
        assertThat(foo.getType(), is(AstNode.Type.VAR));
        assertThat(foo.getName(), is("foo"));
        assertThat(foo.getValue(), is(Value.getNil()));
        assertThat(foo.getValue().getType(), is(Type.NIL));

        final VarNode bar = main.getVariables().get(1);
        assertThat(bar.getType(), is(AstNode.Type.VAR));
        assertThat(bar.getName(), is("bar"));
        assertThat(bar.getValue(), is(Value.getNil()));
        assertThat(bar.getValue().getType(), is(Type.NIL));

        final VarNode baz = main.getVariables().get(2);
        assertThat(baz.getType(), is(AstNode.Type.VAR));
        assertThat(baz.getName(), is("baz"));
        assertThat(baz.getValue(), is(Value.getNil()));
        assertThat(baz.getValue().getType(), is(Type.NIL));
    }

    @Test
    public void parseMultiVaribaleWithAssignemtnWithNewlines() {
        final Parser sut = Parser.forString("var {\n"
                + "  foo = 3.14\n"
                + "  bar = \"snafu\"\n"
                + "  baz = false\n"
                + "}");
        sut.parse();

        final FunctionNode main = sut.getAbstractSyntaxtTree();
        assertThat(main.getType(), is(AstNode.Type.FUNCTION));
        assertThat(main.getVariables().size(), is(3));

        final VarNode foo = main.getVariables().get(0);
        assertThat(foo.getType(), is(AstNode.Type.VAR));
        assertThat(foo.getName(), is("foo"));
        assertThat(foo.getValue(), is(Value.valueOf(3.14f)));
        assertThat(foo.getValue().getType(), is(Type.FLOAT));

        final VarNode bar = main.getVariables().get(1);
        assertThat(bar.getType(), is(AstNode.Type.VAR));
        assertThat(bar.getName(), is("bar"));
        assertThat(bar.getValue(), is(Value.valueOf("snafu")));
        assertThat(bar.getValue().getType(), is(Type.STRING));

        final VarNode baz = main.getVariables().get(2);
        assertThat(baz.getType(), is(AstNode.Type.VAR));
        assertThat(baz.getName(), is("baz"));
        assertThat(baz.getValue(), is(Value.getFalse()));
        assertThat(baz.getValue().getType(), is(Type.BOOLEAN));
    }

    @Test
    public void parseMultiVaribaleWithAndWithoutAssignemtn() {
        final Parser sut = Parser.forString("var {\n"
                + "  foo = 3.14\n"
                + "  bar\n"
                + "  baz = false\n"
                + "}");
        sut.parse();

        final FunctionNode main = sut.getAbstractSyntaxtTree();
        assertThat(main.getType(), is(AstNode.Type.FUNCTION));
        assertThat(main.getVariables().size(), is(3));

        final VarNode foo = main.getVariables().get(0);
        assertThat(foo.getType(), is(AstNode.Type.VAR));
        assertThat(foo.getName(), is("foo"));
        assertThat(foo.getValue(), is(Value.valueOf(3.14f)));
        assertThat(foo.getValue().getType(), is(Type.FLOAT));

        final VarNode bar = main.getVariables().get(1);
        assertThat(bar.getType(), is(AstNode.Type.VAR));
        assertThat(bar.getName(), is("bar"));
        assertThat(bar.getValue(), is(Value.getNil()));
        assertThat(bar.getValue().getType(), is(Type.NIL));

        final VarNode baz = main.getVariables().get(2);
        assertThat(baz.getType(), is(AstNode.Type.VAR));
        assertThat(baz.getName(), is("baz"));
        assertThat(baz.getValue(), is(Value.getFalse()));
        assertThat(baz.getValue().getType(), is(Type.BOOLEAN));
    }

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

    @Test @Ignore
    public void parseSingleIf() {
        final Parser sut = Parser.forString("");

    }

    @Test @Ignore
    public void parseIfElse() {
        final Parser sut = Parser.forString("");

    }

    @Test @Ignore
    public void parseIfElseIf() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseIfElseIfElse() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseSwitch() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseCountingFor() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseEndlessFor() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseConstitionalFor() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseFunctionwithNoArgVoid() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseFunctionWithNoArgAndOneReturn() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseFunctionWithNoArgAndMultiReturn() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseFunctionWithOneArgumentVoid() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseFunctionWithOneArgAndOneReturn() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseFunctionWithOneArgAndMultiReturn() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseFunctionWithMultiArgumentVoid() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseFunctionWithMultiArgAndOneReturn() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseFunctionWithMultiArgAndMultiReturn() {
        final Parser sut = Parser.forString("");
    }

}

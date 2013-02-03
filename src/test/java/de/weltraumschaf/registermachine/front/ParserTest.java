/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.weltraumschaf.registermachine.front;

import de.weltraumschaf.registermachine.inter.AstNode;
import de.weltraumschaf.registermachine.inter.FunctionNode;
import de.weltraumschaf.registermachine.inter.Type;
import de.weltraumschaf.registermachine.inter.Value;
import de.weltraumschaf.registermachine.inter.VarNode;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
public class ParserTest {

    @Test
    public void parseEmptySource() {
        final Parser sut = Parser.forString("");
        sut.parse();

        final FunctionNode main = sut.getAbstractSyntaxtTree();
        assertThat(main.getType(), is(AstNode.Type.FUNCTION));
        assertThat(main.getVariables().size(), is(0));
    }

    @Test
    public void parseSingleVaribaleWithoutAssignemtn() {
        final Parser sut = Parser.forString("var foo");
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
    public void parseSingleVaribaleWithAssignemtn() {
        final Parser sut = Parser.forString("var   foo   =   3.14");
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

    @Test @Ignore
    public void parseMultiVaribaleWithoutAssignemtn() {
        final Parser sut = Parser.forString("var { foo bar baz }");
    }

    @Test @Ignore
    public void parseMultiVaribaleWithAssignemtn() {
        final Parser sut = Parser.forString("var { foo = 3.14 bar = 42 baz = false }");
    }

    @Test @Ignore
    public void parseMultiVaribaleWithAndWithoutAssignemtn() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseSingleConstant() {
        final Parser sut = Parser.forString("");
    }

    @Test @Ignore
    public void parseMultiConstant() {
        final Parser sut = Parser.forString("");
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

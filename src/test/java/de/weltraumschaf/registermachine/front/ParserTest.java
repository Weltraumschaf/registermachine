/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.weltraumschaf.registermachine.front;

import de.weltraumschaf.registermachine.inter.AstNode;
import de.weltraumschaf.registermachine.inter.NopNode;
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

        final AstNode tree = sut.getAbstractSyntaxtTree();
        assertThat(tree, instanceOf(NopNode.class));
        assertThat(tree.getType(), is(AstNode.Type.NOP));
    }

    @Test @Ignore
    public void parseSingleVaribale() {
        final Parser sut = Parser.forString("var foo = 3.14");
        sut.parse();

        final AstNode tree = sut.getAbstractSyntaxtTree();
        assertThat(tree, instanceOf(VarNode.class));
        assertThat(tree.getType(), is(AstNode.Type.VAR));

        final VarNode var = (VarNode) tree;
        assertThat(var.getName(), is("foo"));
        assertThat(var.getValue(), is(Value.valueOf(3.14f)));
    }

    @Test @Ignore
    public void parseMultiVaribale() {
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

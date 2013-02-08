/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.weltraumschaf.registermachine.front;

import de.weltraumschaf.registermachine.inter.AstNode;
import de.weltraumschaf.registermachine.inter.FunctionNode;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
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

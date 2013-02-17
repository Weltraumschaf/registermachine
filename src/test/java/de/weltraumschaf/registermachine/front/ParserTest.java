/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.weltraumschaf.registermachine.front;

import de.weltraumschaf.registermachine.ast.FunctionNode;
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
    }

    @Test @Ignore
    public void parseSwitch() {

    }

    @Test @Ignore
    public void parseCountingFor() {

    }

    @Test @Ignore
    public void parseEndlessFor() {

    }

    @Test @Ignore
    public void parseConstitionalFor() {

    }

    @Test @Ignore
    public void parseFunctionwithNoArgVoid() {

    }

    @Test @Ignore
    public void parseFunctionWithNoArgAndOneReturn() {

    }

    @Test @Ignore
    public void parseFunctionWithNoArgAndMultiReturn() {

    }

    @Test @Ignore
    public void parseFunctionWithOneArgumentVoid() {

    }

    @Test @Ignore
    public void parseFunctionWithOneArgAndOneReturn() {

    }

    @Test @Ignore
    public void parseFunctionWithOneArgAndMultiReturn() {

    }

    @Test @Ignore
    public void parseFunctionWithMultiArgumentVoid() {

    }

    @Test @Ignore
    public void parseFunctionWithMultiArgAndOneReturn() {

    }

    @Test @Ignore
    public void parseFunctionWithMultiArgAndMultiReturn() {

    }

}

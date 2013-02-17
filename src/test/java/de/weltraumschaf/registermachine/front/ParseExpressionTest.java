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
import de.weltraumschaf.registermachine.inter.FunctionNode;
import de.weltraumschaf.registermachine.inter.StatementNode;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ParseExpressionTest {

    @Test @Ignore
    public void parseArithmeticExpression() {
//        final Parser sut = Parser.forString("var foo = 1 + 2 * 3 / 4\n");
//        sut.parse();
//
//        final FunctionNode main = sut.getAbstractSyntaxtTree();
//        assertThat(main.getType(), is(AstNode.Type.FUNCTION));
//        assertThat(main.getStatements().size(), is(1));
//
//        final StatementNode statement = main.getStatements().get(0);
//        assertThat(statement.getType(), is(AstNode.Type.ASSIGNMENT));
    }

    @Test @Ignore
    public void parseArithmeticExpressionWithParens() {
//        final Parser sut = Parser.forString("var foo = 1 * (2 + 3) - 4\n");
//        sut.parse();
//
//        final FunctionNode main = sut.getAbstractSyntaxtTree();
//        assertThat(main.getType(), is(AstNode.Type.FUNCTION));
//        assertThat(main.getStatements().size(), is(1));
//
//        final StatementNode statement = main.getStatements().get(0);
//        assertThat(statement.getType(), is(AstNode.Type.ASSIGNMENT));
    }

}

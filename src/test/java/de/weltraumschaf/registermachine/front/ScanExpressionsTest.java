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

import de.weltraumschaf.commons.Null;
import de.weltraumschaf.commons.token.Token;
import de.weltraumschaf.commons.token.TokenType;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ScanExpressionsTest {

    @Test public void scanArithmeticExpression() {
        final Scanner sut = Scanner.forString("1 + 2 * 3 / 4");

        Token<?> token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(((Token<Integer>) token).getValue(), is(1));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("+"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(((Token<Integer>) token).getValue(), is(2));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("*"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(((Token<Integer>) token).getValue(), is(3));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("/"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(((Token<Integer>) token).getValue(), is(4));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test public void scanArithmeticExpressionWithParens() {
        final Scanner sut = Scanner.forString("1 * (2 + 3) - 4");

        Token<?> token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(((Token<Integer>) token).getValue(), is(1));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("*"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("("));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(((Token<Integer>) token).getValue(), is(2));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("+"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(((Token<Integer>) token).getValue(), is(3));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is(")"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("-"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(((Token<Integer>) token).getValue(), is(4));


        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));

        assertThat(sut.hasNext(), is(false));
    }

}

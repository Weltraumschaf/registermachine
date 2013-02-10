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
import de.weltraumschaf.commons.token.Position;
import de.weltraumschaf.commons.token.Token;
import de.weltraumschaf.commons.token.TokenType;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ScanVariablesTest {

    @Test
    public void parseMultiVaribaleWithAssignemtnWithNewlines() {
        final Scanner sut = Scanner.forString("var {\n"
                + "  foo = 3.14\n"
                + "  bar = \"snafu\"\n"
                + "  baz = false\n"
                + "}");

        Token<?> token = sut.getCurrentToken();

        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(((Token<String>) token).getValue(), is("var"));
        assertThat(token.getPosition(), is(new Position(1, 1)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("{"));
        assertThat(token.getPosition(), is(new Position(1, 5)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(token.getPosition(), is(new Position(1, 6)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(((Token<String>) token).getValue(), is("foo"));
        assertThat(token.getPosition(), is(new Position(2, 3)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("="));
        assertThat(token.getPosition(), is(new Position(2, 7)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.FLOAT));
        assertThat(((Token<Float>) token).getValue(), is(3.14f));
        assertThat(token.getPosition(), is(new Position(2, 9)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(token.getPosition(), is(new Position(2, 13)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(((Token<String>) token).getValue(), is("bar"));
        assertThat(token.getPosition(), is(new Position(3, 3)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("="));
        assertThat(token.getPosition(), is(new Position(3, 7)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(((Token<String>) token).getValue(), is("snafu"));
        assertThat(token.getPosition(), is(new Position(3, 9)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(token.getPosition(), is(new Position(3, 16)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(((Token<String>) token).getValue(), is("baz"));
        assertThat(token.getPosition(), is(new Position(4, 3)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("="));
        assertThat(token.getPosition(), is(new Position(4, 7)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.BOOLEAN));
        assertThat(((Token<Boolean>) token).getValue(), is(false));
        assertThat(token.getPosition(), is(new Position(4, 9)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(token.getPosition(), is(new Position(4, 14)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("}"));
        assertThat(token.getPosition(), is(new Position(5, 1)));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(token.getPosition(), is(new Position(5, 1)));

        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanMultipleVariableDeclaration() {
        final Scanner sut = Scanner.forString("var {\n"
                + "  foo\n"
                + "  bar\n"
                + "  baz\n"
                + "}");

        Token<?> token = sut.getCurrentToken();

        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(((Token<String>) token).getValue(), is("var"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("{"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(((Token<String>) token).getValue(), is("foo"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(((Token<String>) token).getValue(), is("bar"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(((Token<String>) token).getValue(), is("baz"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("}"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));

        assertThat(sut.hasNext(), is(false));
    }

}

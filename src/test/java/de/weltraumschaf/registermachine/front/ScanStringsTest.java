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
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ScanStringsTest {

    // CHECKSTYLE:OFF
    @Rule public ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON

    @Test
    public void scanString() {
        final Scanner sut = Scanner.forString(" \" this is a\nstring  \" ");
        Token<?> token = sut.getCurrentToken();

        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(((Token<String>) token).getValue(), is(" this is a\nstring  "));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
    }

    @Test
    public void scanString_empty() {
        final Scanner sut = Scanner.forString(" \"\" ");
        Token<?> token = sut.getCurrentToken();

        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(((Token<String>) token).getValue(), is(""));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
    }

    @Test
    public void scanString_throwExceptionIfUnterminated() {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Unterminated string!");
        final Scanner sut = Scanner.forString(" \" this is");
        sut.getCurrentToken();
    }

    @Test
    public void scanString_throwExceptionIfEmpty() {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Unterminated string!");
        final Scanner sut = Scanner.forString(" \"");
        sut.getCurrentToken();
    }

}

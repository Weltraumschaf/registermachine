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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ScanCommentsTest {

    // CHECKSTYLE:OFF
    @Rule public ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON

    @Test
    public void scanSingleLineComment_withoutNewline() {
        final Scanner sut = Scanner.forString("   // this is a comment");
        sut.next();

        Token<?> token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.COMMENT));
        assertThat(((Token<String>) token).getValue(), is("// this is a comment"));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanSingleLineComment_withNewline() {
        final Scanner sut = Scanner.forString("   // this is a comment\n");
        sut.next();

        Token<?> token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.COMMENT));
        assertThat(((Token<String>) token).getValue(), is("// this is a comment"));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanSingleLineComment_withTwoNewlines() {
        final Scanner sut = Scanner.forString("   // this is a comment\n\n");
        sut.next();

        Token<?> token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.COMMENT));
        assertThat(((Token<String>) token).getValue(), is("// this is a comment"));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanMultiLineComment_throwSyntaxExceptionIfUnterminated() {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Unterminated multiline comment!");
        final Scanner sut = Scanner.forString("   /* this is a comment\nand one more line\n\nlast line ");
        sut.next();
    }

    @Test
    public void scanMultiLineComment_withoutNewline() {
        final Scanner sut = Scanner.forString("   /* this is a comment\nand one more line\n\nlast line */");
        sut.next();

        Token<?> token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.COMMENT));
        assertThat(((Token<String>) token).getValue(), is("/* this is a comment\nand one more line\n\nlast line */"));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanMultiLineComment_withOneNewline() {
        final Scanner sut = Scanner.forString("   /* this is a comment\nand one more line\n\nlast line */\n");
        Token<?> token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.COMMENT));
        assertThat(((Token<String>) token).getValue(), is("/* this is a comment\nand one more line\n\nlast line */"));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanMultiLineComment_withTwoNewline() {
        final Scanner sut = Scanner.forString("   /* this is a comment\nand one more line\n\nlast line */\n\n");
        Token<?> token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.COMMENT));
        assertThat(((Token<String>) token).getValue(), is("/* this is a comment\nand one more line\n\nlast line */"));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

}

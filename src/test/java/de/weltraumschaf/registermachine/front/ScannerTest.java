/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a beer in return.
 *
 */
package de.weltraumschaf.registermachine.front;

import de.weltraumschaf.commons.Null;
import de.weltraumschaf.commons.token.Token;
import de.weltraumschaf.commons.token.TokenType;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
public class ScannerTest {

    @Test
    public void invokeNextIfCurrentTokenIsNull() {
        final Scanner sut = Scanner.forString("");
        final Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>)token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanEmpty() {
        final Scanner sut = Scanner.forString("");
        sut.next();

        final Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>)token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test 
    public void scanWhitespaces() {
        final Scanner sut = Scanner.forString("  \t   \n    ");
        sut.next();

        final Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>)token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test @Ignore
    public void scanSingleLineComment() {
        final Scanner sut = Scanner.forString("   // this is a comment");
        sut.next();

        Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.COMMENT));
        assertThat(((Token<String>)token).getValue(), is("// this is a comment"));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>)token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test @Ignore
    public void scanMultiLineComment() {
        final Scanner sut = Scanner.forString("   /* this is a comment\nand one more line\n\nlast line */");
        sut.next();

        Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.COMMENT));
        assertThat(((Token<String>)token).getValue(), is("/* this is a comment\nand one more line\n\nlast line */"));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>)token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test @Ignore
    public void scanKwyword() {

    }

}

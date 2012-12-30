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
package de.weltraumschaf.registermachine.asm;

import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class LineScannerTest {

    private final LineScanner sut = new LineScanner();

    @Test
    public void scanComment() throws AssemblerSyntaxException {
        List<Token> tokens = sut.parse("; a comment");
        assertThat(tokens.size(), is(0));
        tokens = sut.parse("   ; a comment");
        assertThat(tokens.size(), is(0));
    }

    @Test
    public void scanEmpty() throws AssemblerSyntaxException {
        List<Token> tokens = sut.parse("");
        assertThat(tokens.size(), is(0));
        tokens = sut.parse("     ");
        assertThat(tokens.size(), is(0));
        tokens = sut.parse("          ");
        assertThat(tokens.size(), is(0));
    }

    @Test
    public void scanLiteral() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse("  snafu ");
        assertThat(tokens.size(), is(1));
        final Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.getValue(), is("snafu"));
    }

    @Test
    public void scanOpCode() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse("  add ");
        assertThat(tokens.size(), is(1));
        final Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.OPCODE));
        assertThat(token.getValue(), is("add"));
    }

    @Test
    public void scan_functionMetaCode() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse(".function");
        assertThat(tokens.size(), is(1));
        final Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.METACODE));
        assertThat(token.getValue(), is(".function"));
    }

    @Test
    public void scan_localMetaCode() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse("    .local   ");
        assertThat(tokens.size(), is(1));
        final Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.METACODE));
        assertThat(token.getValue(), is(".local"));
    }

    @Test
    public void scan_contMetaCode() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse(".const   ");
        assertThat(tokens.size(), is(1));
        final Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.METACODE));
        assertThat(token.getValue(), is(".const"));
    }

    @Test
    public void scan_string() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse("  \"foo  bar;\"   ");
        assertThat(tokens.size(), is(1));
        final Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(token.getValue(), is("foo  bar;"));
    }


    @Test(expected=AssemblerSyntaxException.class) @Ignore
    public void scan_unterminatedEmptyString() throws AssemblerSyntaxException {
        sut.parse("  \"");
    }

    @Test(expected=AssemblerSyntaxException.class)
    public void scan_unterminatedString() throws AssemblerSyntaxException {
        sut.parse("  \"foo  ");
    }

    @Test
    public void scanFloat() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse("  3.1415  ");
        assertThat(tokens.size(), is(1));
        final Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.FLOAT));
        assertThat(token.getValue(), is("3.1415"));
    }

    @Test
    public void scanInteger() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse("  23  ");
        assertThat(tokens.size(), is(1));
        final Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(token.getValue(), is("23"));
    }

}
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
    public void scan_comment() throws AssemblerSyntaxException {
        List<Token> tokens = sut.parse("; a comment");
        assertThat(tokens.size(), is(0));
        tokens = sut.parse("   ; a comment");
        assertThat(tokens.size(), is(0));
    }

    @Test
    public void scan_EmptLiney() throws AssemblerSyntaxException {
        List<Token> tokens = sut.parse("");
        assertThat(tokens.size(), is(0));
        tokens = sut.parse("     ");
        assertThat(tokens.size(), is(0));
        tokens = sut.parse("          ");
        assertThat(tokens.size(), is(0));
    }

    @Test
    public void scan_literal() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse("  snafu ");
        assertThat(tokens.size(), is(1));
        final Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(token.getValue(), is("snafu"));
    }

    @Test
    public void scan_opCode() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse("  add ");
        assertThat(tokens.size(), is(1));
        final Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.OPCODE));
        assertThat(token.getValue(), is("add"));
    }

    @Test
    public void scan_localMetaCodeWithStringArg() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse(".local \"b\"");
        assertThat(tokens.size(), is(2));

        final Token metaCodeToken = tokens.get(0);
        assertThat(metaCodeToken.getType(), is(TokenType.METACODE));
        assertThat(metaCodeToken.getValue(), is(".local"));

        final Token nupsToken = tokens.get(1);
        assertThat(nupsToken.getType(), is(TokenType.STRING));
        assertThat(nupsToken.getValue(), is("b"));
    }

    @Test
    public void scan_localMetaCodeWithIntegerArg() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse(".local 42");
        assertThat(tokens.size(), is(2));

        final Token metaCodeToken = tokens.get(0);
        assertThat(metaCodeToken.getType(), is(TokenType.METACODE));
        assertThat(metaCodeToken.getValue(), is(".local"));

        final Token nupsToken = tokens.get(1);
        assertThat(nupsToken.getType(), is(TokenType.INTEGER));
        assertThat(nupsToken.getValue(), is("42"));
    }

    @Test
    public void scan_constMetaCodeWithStringArgs() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse(".const \"a\"");
        assertThat(tokens.size(), is(2));

        final Token metaCodeToken = tokens.get(0);
        assertThat(metaCodeToken.getType(), is(TokenType.METACODE));
        assertThat(metaCodeToken.getValue(), is(".const"));

        final Token nupsToken = tokens.get(1);
        assertThat(nupsToken.getType(), is(TokenType.STRING));
        assertThat(nupsToken.getValue(), is("a"));
    }

    @Test
    public void scan_constMetaCodeWithIntegerArgs() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse(".const 23");
        assertThat(tokens.size(), is(2));

        final Token metaCodeToken = tokens.get(0);
        assertThat(metaCodeToken.getType(), is(TokenType.METACODE));
        assertThat(metaCodeToken.getValue(), is(".const"));

        final Token nupsToken = tokens.get(1);
        assertThat(nupsToken.getType(), is(TokenType.INTEGER));
        assertThat(nupsToken.getValue(), is("23"));
    }

    @Test
    public void scan_constMetaCodeWithOneCharIntegerArgs() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse(".const 2");
        assertThat(tokens.size(), is(2));

        final Token metaCodeToken = tokens.get(0);
        assertThat(metaCodeToken.getType(), is(TokenType.METACODE));
        assertThat(metaCodeToken.getValue(), is(".const"));

        final Token nupsToken = tokens.get(1);
        assertThat(nupsToken.getType(), is(TokenType.INTEGER));
        assertThat(nupsToken.getValue(), is("2"));
    }

    @Test
    public void scan_constMetaCodeWithOneCharStringArgs() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse(".const a");
        assertThat(tokens.size(), is(2));

        final Token metaCodeToken = tokens.get(0);
        assertThat(metaCodeToken.getType(), is(TokenType.METACODE));
        assertThat(metaCodeToken.getValue(), is(".const"));

        final Token nupsToken = tokens.get(1);
        assertThat(nupsToken.getType(), is(TokenType.LITERAL));
        assertThat(nupsToken.getValue(), is("a"));
    }

    @Test
    public void scan_functionMetaCodeWithArgs() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse(".function 0 0 4 2");
        assertThat(tokens.size(), is(5));

        final Token metaCodeToken = tokens.get(0);
        assertThat(metaCodeToken.getType(), is(TokenType.METACODE));
        assertThat(metaCodeToken.getValue(), is(".function"));

        final Token nupsToken = tokens.get(1);
        assertThat(nupsToken.getType(), is(TokenType.INTEGER));
        assertThat(nupsToken.getValue(), is("0"));

        final Token numparamsToken = tokens.get(2);
        assertThat(numparamsToken.getType(), is(TokenType.INTEGER));
        assertThat(numparamsToken.getValue(), is("0"));

        final Token isVarargToken = tokens.get(3);
        assertThat(isVarargToken.getType(), is(TokenType.INTEGER));
        assertThat(isVarargToken.getValue(), is("4"));

        final Token maxStackSizeToken = tokens.get(4);
        assertThat(maxStackSizeToken.getType(), is(TokenType.INTEGER));
        assertThat(maxStackSizeToken.getValue(), is("2"));
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


    @Test(expected=AssemblerSyntaxException.class)
    public void scan_unterminatedEmptyString() throws AssemblerSyntaxException {
        sut.parse("  \"");
    }

    @Test(expected=AssemblerSyntaxException.class)
    public void scan_unterminatedString() throws AssemblerSyntaxException {
        sut.parse("  \"foo  ");
    }

    @Test
    public void scan_float() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse("  3.1415  ");
        assertThat(tokens.size(), is(1));
        final Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.FLOAT));
        assertThat(token.getValue(), is("3.1415"));
    }

    @Test
    public void scan_integer() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse("  23  ");
        assertThat(tokens.size(), is(1));
        final Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(token.getValue(), is("23"));
    }

    @Test
    public void scan_variousTokens() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse(".function 23 \"foo\" 42 .local 11");
        assertThat(tokens.size(), is(6));

        Token token = tokens.get(0);
        assertThat(token.getType(), is(TokenType.METACODE));
        assertThat(token.getValue(), is(".function"));

        token = tokens.get(1);
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(token.getValue(), is("23"));

        token = tokens.get(2);
        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(token.getValue(), is("foo"));

        token = tokens.get(3);
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(token.getValue(), is("42"));

        token = tokens.get(4);
        assertThat(token.getType(), is(TokenType.METACODE));
        assertThat(token.getValue(), is(".local"));

        token = tokens.get(5);
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(token.getValue(), is("11"));
    }

}
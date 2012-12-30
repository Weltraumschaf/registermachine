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
    @Test @Ignore
    public void scanLiteral() {
    }

    @Test @Ignore
    public void scanOpCode() {
    }

    @Test
    public void scanMetaCode() throws AssemblerSyntaxException {
        final List<Token> tokens = sut.parse(".function");
        assertThat(tokens.size(), is(1));
    }

    @Test @Ignore
    public void scanString() {
    }

    @Test @Ignore
    public void scanFloat() {
    }

    @Test @Ignore
    public void scanInteger() {
    }
}
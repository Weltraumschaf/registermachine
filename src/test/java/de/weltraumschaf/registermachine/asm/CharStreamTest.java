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

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CharStreamTest {

    @Test
    public void isEmpty() {
        CharStream sut = new CharStream("");
        assertThat(sut.isEmpty(), is(true));
        assertThat(sut.hasNextChar(), is(false));

        sut = new CharStream("  ");
        assertThat(sut.isEmpty(), is(false));
        assertThat(sut.hasNextChar(), is(true));
    }

    @Test public void iterateOverCharacters() {
        final CharStream sut = new CharStream("foo");
        assertThat(sut.isEmpty(), is(false));

        assertThat(sut.getCurrentChar(), is('f'));
        assertThat(sut.getIndex(), is(0));
        assertThat(sut.hasNextChar(), is(true));
        sut.nextChar();

        assertThat(sut.getCurrentChar(), is('o'));
        assertThat(sut.getIndex(), is(1));
        assertThat(sut.hasNextChar(), is(true));
        sut.nextChar();

        assertThat(sut.getCurrentChar(), is('o'));
        assertThat(sut.getIndex(), is(2));
        assertThat(sut.hasNextChar(), is(false));
    }

}
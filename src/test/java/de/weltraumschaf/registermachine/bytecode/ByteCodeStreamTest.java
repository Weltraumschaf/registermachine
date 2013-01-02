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

package de.weltraumschaf.registermachine.bytecode;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ByteCodeStreamTest {

    @Test
    public void emptyArray() {
        final ByteCodeStream sut = new ByteCodeStream(new byte[]{});
        assertThat(sut.isEmpty(), is(true));
        assertThat(sut.hasNextByte(), is(false));
        assertThat(sut.getIndex(), is(0));
    }

    @Test
    public void threeElementArray() {
        final ByteCodeStream sut = new ByteCodeStream(new byte[]{1, 2, 3});
        assertThat(sut.isEmpty(), is(false));
        assertThat(sut.hasNextByte(), is(true));
        assertThat(sut.getIndex(), is(0));

        assertThat(sut.getCurrentByte(), is((byte) 1));
        sut.nextByte();

        assertThat(sut.hasNextByte(), is(true));
        assertThat(sut.getIndex(), is(1));
        assertThat(sut.getCurrentByte(), is((byte) 2));
        sut.nextByte();

        assertThat(sut.hasNextByte(), is(false));
        assertThat(sut.getIndex(), is(2));
        assertThat(sut.getCurrentByte(), is((byte) 3));
    }

}
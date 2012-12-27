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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ByteCodeTest {

    @Test
    public void testToString() {
        assertThat(ByteCode.ADD.toString(), is("ADD[0x0c]"));
    }

    @Test
    public void toHex() {
        assertThat(ByteCode.toHex((byte) 3), is("03"));
        assertThat(ByteCode.toHex((byte) 15), is("0f"));
        assertThat(ByteCode.toHex((byte) 16), is("10"));
        assertThat(ByteCode.toHex((byte) 27), is("1b"));
    }

    @Test
    public void lookup() {
        assertThat(ByteCode.lokup("move"), is(ByteCode.MOVE));
        assertThat(ByteCode.lokup("add"), is(ByteCode.ADD));
        assertThat(ByteCode.lokup("ADD"), is(ByteCode.ADD));
        assertThat(ByteCode.lokup("adD"), is(ByteCode.ADD));
        assertThat(ByteCode.lokup("foobar"), is(ByteCode.UNKWONN));
    }
}
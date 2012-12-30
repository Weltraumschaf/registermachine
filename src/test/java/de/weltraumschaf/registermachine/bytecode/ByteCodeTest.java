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
        assertThat(OpCode.ADD.toString(), is("ADD[0x03]"));
    }

    @Test
    public void toHex() {
        assertThat(OpCode.toHex((byte) 3), is("03"));
        assertThat(OpCode.toHex((byte) 15), is("0f"));
        assertThat(OpCode.toHex((byte) 16), is("10"));
        assertThat(OpCode.toHex((byte) 27), is("1b"));
    }

    @Test
    public void lookup() {
        assertThat(OpCode.lokup("move"), is(OpCode.MOVE));
        assertThat(OpCode.lokup("add"), is(OpCode.ADD));
        assertThat(OpCode.lokup("ADD"), is(OpCode.ADD));
        assertThat(OpCode.lokup("adD"), is(OpCode.ADD));
        assertThat(OpCode.lokup("foobar"), is(OpCode.UNKWONN));
    }
}

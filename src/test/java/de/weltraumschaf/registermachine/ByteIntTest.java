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
package de.weltraumschaf.registermachine;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ByteIntTest {

    @Test
    public void bytesFromShort() {
        assertThat(ByteInt.bytesFromShort((short) 0), is(new byte[] { (byte) 0x00, (byte) 0x00}));
        assertThat(ByteInt.bytesFromShort((short) 1), is(new byte[] { (byte) 0x01, (byte) 0x00}));
        assertThat(ByteInt.bytesFromShort((short) 255), is(new byte[] { (byte) 0xFF, (byte) 0x00}));
        assertThat(ByteInt.bytesFromShort((short) 256), is(new byte[] { (byte) 0x00, (byte) 0x01}));
        assertThat(ByteInt.bytesFromShort((short) 511), is(new byte[] { (byte) 0xFF, (byte) 0x01}));
        assertThat(ByteInt.bytesFromShort((short) 65535), is(new byte[] { (byte) 0xFF, (byte) 0xFF}));
    }

    @Test
    public void shortFromBytes() {
        assertThat(ByteInt.shortFromBytes(new byte[] { (byte) 0x00, (byte) 0x00}), is((short) 0));
        assertThat(ByteInt.shortFromBytes(new byte[] { (byte) 0x01, (byte) 0x00}), is((short) 1));
        assertThat(ByteInt.shortFromBytes(new byte[] { (byte) 0xFF, (byte) 0x00}), is((short) 255));
        assertThat(ByteInt.shortFromBytes(new byte[] { (byte) 0x00, (byte) 0x01}), is((short) 256));
        assertThat(ByteInt.shortFromBytes(new byte[] { (byte) 0xFF, (byte) 0x01}), is((short) 511));
        assertThat(ByteInt.shortFromBytes(new byte[] { (byte) 0xFF, (byte) 0xFF}), is((short) 65535));
    }

    @Test
    public void bytesFromInt() {
        assertThat(ByteInt.bytesFromInt(0), is(new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00}));
        assertThat(ByteInt.bytesFromInt(1), is(new byte[] { (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00}));
        assertThat(ByteInt.bytesFromInt(255), is(new byte[] { (byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0x00}));
        assertThat(ByteInt.bytesFromInt(256), is(new byte[] { (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00}));
        assertThat(ByteInt.bytesFromInt(511), is(new byte[] { (byte) 0xFF, (byte) 0x01, (byte) 0x00, (byte) 0x00}));
        assertThat(ByteInt.bytesFromInt(65535), is(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0x00}));
    }

    @Test
    public void intFromBytes() {
        assertThat(ByteInt.intFromBytes(new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00}), is(0));
        assertThat(ByteInt.intFromBytes(new byte[] { (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00}), is(1));
        assertThat(ByteInt.intFromBytes(new byte[] { (byte) 0xFF, (byte) 0x00, (byte) 0x00, (byte) 0x00}), is(255));
        assertThat(ByteInt.intFromBytes(new byte[] { (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00}), is(256));
        assertThat(ByteInt.intFromBytes(new byte[] { (byte) 0xFF, (byte) 0x01, (byte) 0x00, (byte) 0x00}), is(511));
        assertThat(ByteInt.intFromBytes(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0x00}), is(65535));
    }

}

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

package de.weltraumschaf.registermachine.convert;

import java.io.UnsupportedEncodingException;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ByteStringTest {

    @Test
    public void bytesFromChar() throws UnsupportedEncodingException {
        assertThat(ByteString.bytesFromChar('a'), is(new byte[]{ 0x61 }));
        assertThat(ByteString.bytesFromChar('ü'), is(new byte[]{ (byte) 0xC3, (byte) 0xBC, }));
        assertThat(ByteString.bytesFromChar('ý'), is(new byte[]{ (byte) 0xC3, (byte) 0xBD, }));
        assertThat(ByteString.bytesFromChar('셨'), is(new byte[]{ (byte) 0xEC, (byte) 0x85, (byte) 0xA8, }));
    }

    @Test
    public void charFromBytes() throws UnsupportedEncodingException {
        assertThat(ByteString.charFromBytes(new byte[]{ 0x61 }), is('a'));
        assertThat(ByteString.charFromBytes(new byte[]{ (byte) 0xC3, (byte) 0xBC, }), is('ü'));
        assertThat(ByteString.charFromBytes(new byte[]{ (byte) 0xC3, (byte) 0xBD, }), is('ý'));
        assertThat(ByteString.charFromBytes(new byte[]{ (byte) 0xEC, (byte) 0x85, (byte) 0xA8, }), is('셨'));
    }

    @Test
    public void bytesFromString() throws UnsupportedEncodingException {
        assertThat(ByteString.bytesFromString("a"), is(new byte[]{ 0x61 }));
        assertThat(ByteString.bytesFromString("empty_main_function.ctasm"),
                is(new byte[]{ 0x65, 0x6d, 0x70, 0x74, 0x79, 0x5f, 0x6d, 0x61, 0x69, 0x6e, 0x5f, 0x66, 0x75, 0x6e, 0x63, 0x74, 0x69, 0x6f, 0x6e, 0x2e, 0x63, 0x74, 0x61, 0x73, 0x6d, }));
        assertThat(ByteString.bytesFromString("hello"), is(new byte[]{ 0x68, 0x65, 0x6C, 0x6C, 0x6F, }));
        assertThat(ByteString.bytesFromString("aüý셨"),
                is(new byte[]{ 0x61, (byte) 0xC3, (byte) 0xBC, (byte) 0xC3, (byte) 0xBD, (byte) 0xEC, (byte) 0x85,
                    (byte) 0xA8, }));
    }

    @Test
    public void stringFromBytes() throws UnsupportedEncodingException {
        assertThat(ByteString.stringFromBytes(new byte[]{ 0x61 }), is("a"));
        assertThat(ByteString.stringFromBytes(new byte[]{ 0x61, (byte) 0xC3, (byte) 0xBC, (byte) 0xC3, (byte) 0xBD,
            (byte) 0xEC, (byte) 0x85, (byte) 0xA8, }), is("aüý셨"));
    }

}

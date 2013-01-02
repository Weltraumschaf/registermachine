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

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ByteCodeFileTest {

    @Test
    public void isValid_badHeader() throws IOException {
        final ByteCodeFile sut = new ByteCodeFile(new byte[]{(byte) 0xCA, (byte) 0xFF, (byte) 0x01});
        assertThat(sut.isValid(), is(false));
    }

    @Test
    public void isValid_shortHeader() throws IOException {
        ByteCodeFile sut = new ByteCodeFile(new byte[]{(byte) 0xCA});
        assertThat(sut.isValid(), is(false));
        sut = new ByteCodeFile(new byte[]{(byte) 0xCA, 0x7E});
        assertThat(sut.isValid(), is(false));
    }

    @Test
    public void isValid_emptyFile() throws IOException {
        final ByteCodeFile sut = new ByteCodeFile(new byte[]{});
        assertThat(sut.isValid(), is(false));
    }

    @Test
    public void isValid_goodHeader() throws IOException {
        final ByteCodeFile sut = new ByteCodeFile(new byte[]{(byte) 0xCA, (byte) 0x7E, (byte) 0x01, (byte) 0x00});
        assertThat(sut.isValid(), is(true));
    }

    @Test
    public void getVersion() {
        final ByteCodeFile sut = new ByteCodeFile(new byte[]{(byte) 0xCA, (byte) 0x7E, (byte) 0x01});
        assertThat(sut.getVersion(), is((short) 0x01));
    }

    @Test
    public void getProgramm() {
        final ByteCodeFile sut = new ByteCodeFile(new byte[]{
            (byte) 0xCA, (byte) 0x7E, (byte) 0x01, (byte) 0x00, (byte) 0x03, (byte) 0x04, (byte) 0x05, });
        assertThat(sut.getProgramm(), is(new byte[]{(byte) 0x03, (byte) 0x04, (byte) 0x05}));
    }
}

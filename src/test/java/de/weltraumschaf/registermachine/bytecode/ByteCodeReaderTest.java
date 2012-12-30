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
import java.io.InputStream;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ByteCodeReaderTest {

    private static final String PACKAGE_PREFIX = "/de/weltraumschaf/registermachine/bytecode";
    private ByteCodeReader sut;

    @After
    public void closeReader() {
        if (null != sut) {
            sut.close();
        }
    }

    private ByteCodeReader createFromPackageResource(final String resourcePath) {
        final InputStream input = getClass().getResourceAsStream(PACKAGE_PREFIX + resourcePath);
        return new ByteCodeReader(input);
    }
    @Test
    public void read_fileWIthHeader() throws IOException {
        sut = createFromPackageResource("/header.ct");
        final byte[] bytecode = sut.read();
        assertThat(bytecode.length, is(2));
        assertThat(bytecode[0], is((byte) 0xCA));
        assertThat(bytecode[1], is((byte) 0x7E));
    }

    @Test
    public void read_badHeader() throws IOException {
        sut = createFromPackageResource("/bad_header.ct");
        final byte[] bytecode = sut.read();
        assertThat(bytecode.length, is(2));
        assertThat(bytecode[0], is((byte) 0xFF));
        assertThat(bytecode[1], is((byte) 0xAB));
    }

    @Test
    public void read_shortHeader() throws IOException {
        sut = createFromPackageResource("/short_header.ct");
        final byte[] bytecode = sut.read();
        assertThat(bytecode.length, is(1));
        assertThat(bytecode[0], is((byte) 0xCA));
    }

    @Test
    public void read_emptyFile() throws IOException {
        sut = createFromPackageResource("/empty.ct");
        final byte[] bytecode = sut.read();
        assertThat(bytecode.length, is(0));
    }

}

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
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ByteCodeReader {

    private final InputStream input;

    public ByteCodeReader(final InputStream input) {
        this.input = input;
    }

    public byte[] read() throws IOException {
        return IOUtils.toByteArray(input);
    }

    public void close() {
        IOUtils.closeQuietly(input);
    }

}

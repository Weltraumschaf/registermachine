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
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;

/**
 * Writes byte code to file.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ByteCodeWriter {

    /**
     * Stream used to write.
     */
    private final OutputStream output;

    /**
     * Dedicated constructor.
     *
     * @param output stream to write to
     */
    public ByteCodeWriter(final OutputStream output) {
        super();
        this.output = output;
    }

    /**
     * Writes {@link ByteCodeFile}.
     *
     * @param code byte code to write
     * @throws IOException if, file I/O errors happened
     */
    public void write(final ByteCodeFile code) throws IOException {
        write(code.toArray());
    }

    /**
     * Write byte array to file.
     *
     * @param code byte code array
     * @throws IOException if, file I/O errors happened
     */
    public void write(final byte[] code) throws IOException {
        IOUtils.write(code, output);
        IOUtils.closeQuietly(output);
    }

}

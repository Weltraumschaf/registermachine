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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Helper to create file I/O streams.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class FileIo {

    /**
     * Hide constructor for pure static helper class.
     */
    private FileIo() {
        super();
    }

    /**
     * Creates a new input stream.
     *
     * @param filename filename to create for
     * @return stream to read from
     * @throws FileNotFoundException if, file does not exists
     */
    static InputStream newInputStream(final String filename) throws FileNotFoundException {
        return new FileInputStream(new File(filename));
    }

    /**
     * Creates a new output stream.
     *
     * @param filename filename to create for
     * @return stream to write to
     * @throws FileNotFoundException if, file does not exists
     */
    static OutputStream newOutputStream(final String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(filename));
    }

}

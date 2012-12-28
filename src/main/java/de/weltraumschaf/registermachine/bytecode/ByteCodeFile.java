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

import de.weltraumschaf.registermachine.Const;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Represents a byte code file.
 *
 * Format
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ByteCodeFile {

    private final byte[] bytecode;

    public ByteCodeFile(final InputStream input) throws IOException {
        this(new ByteCodeReader(input));
    }

    public ByteCodeFile(final ByteCodeReader bytecode) throws IOException {
        super();
        this.bytecode = bytecode.read();
        bytecode.close();
    }

    public ByteCodeFile(final byte[] bytecode) {
        super();
        this.bytecode = bytecode;
    }

    public boolean isValid() {
        if (bytecode.length < 3) {
            return false;
        }

        return bytecode[0] == Const.BC_FST_HEADER_BYTE && bytecode[1] == Const.BC_SND_HEADER_BYTE;
    }

    public byte getVersion() {
        return bytecode[2];
    }

    public byte[] getProgramm() {
        if (bytecode.length < 4) {
            return new byte[0];
        }
        return Arrays.copyOfRange(bytecode, 3, bytecode.length);
    }

    public byte[] toArray() {
        return bytecode;
    }

}

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

import de.weltraumschaf.registermachine.convert.ByteInt;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Represents a byte code file.
 *
 * Format:
 * <pre>
 * [ 2 bytes header: 0xca 0x7e ]
 * [ 2 bytes version           ]
 * [ 1 ... 13 bytes opcode     ]
 * ...
 * [ 1 ... 13 bytes opcode     ]
 * </pre>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ByteCodeFile {

    /** Byte position of version start. */
    private static final int VERSION_OFFSET = 2;
    /** Byte position of code start. */
    private static final int CODE_OFFSTET = 4;

    /**
     * The byte code w/o header.
     *
     * TODO Use ByteCodeStream
     */
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
        if (bytecode.length < CODE_OFFSTET) {
            return false;
        }

        return bytecode[0] == ByteCodeStream.BC_FST_HEADER_BYTE && bytecode[1] == ByteCodeStream.BC_SND_HEADER_BYTE;
    }

    public short getVersion() {
        return ByteInt.shortFromBytes(Arrays.copyOfRange(bytecode, VERSION_OFFSET, CODE_OFFSTET));
    }

    public byte[] getProgramm() {
        if (bytecode.length <= CODE_OFFSTET) {
            return new byte[0];
        }
        return Arrays.copyOfRange(bytecode, CODE_OFFSTET, bytecode.length);
    }

    public byte[] toArray() {
        return bytecode;
    }

}

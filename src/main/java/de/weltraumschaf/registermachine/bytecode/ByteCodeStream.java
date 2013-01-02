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

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final public class ByteCodeStream {

    public static final byte BC_FST_HEADER_BYTE = (byte) 0xCA;
    public static final byte BC_SND_HEADER_BYTE = (byte) 0x7E;
    public static final short BC_CURRENT_VERSION = (short) 0x01;
    public static final int ARG_BYTE_COUNT = 4;
    private final byte[] bytes;
    /**
     * Current character position.
     */
    private int index;

    public ByteCodeStream(final byte[] str) {
        this.bytes = str;
    }

    boolean isEmpty() {
        return bytes.length == 0;
    }

    /**
     *
     * @return @throws IndexOutOfBoundsException
     */
    byte getCurrentByte() {
        return bytes[index];
    }

    void nextByte() {
        if (hasNextByte()) {
            ++index;
        }
    }

    boolean hasNextByte() {
        if (isEmpty()) {
            return false;
        }

        return index < bytes.length - 1;
    }

    int getIndex() {
        return index;
    }
}

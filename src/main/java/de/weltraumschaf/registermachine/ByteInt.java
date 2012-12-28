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

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ByteInt {

    private ByteInt() {
        super();
    }

    public static byte[] bytesFromInt(int i) {
        final byte[] bytes = new byte[4];
        bytes[0] = (byte) i;
        bytes[1] = (byte) (i >> 8);
        bytes[2] = (byte) (i >> 16);
        bytes[3] = (byte) (i >> 24);
        return bytes;
    }

    public static int intFromBytes(final byte[] bytes) {
        if (bytes.length != 4) {
            throw new IllegalArgumentException("Need 4 bytes exactly!");
        }

        int value = bytes[0] & 0xFF;
        value |= (bytes[1] << 8 ) & 0xFFFF;
        value |= (bytes[2] << 16) & 0xFFFFFF;
        value |= (bytes[3] << 24) & 0xFFFFFFFF;
        return value;
    }
}

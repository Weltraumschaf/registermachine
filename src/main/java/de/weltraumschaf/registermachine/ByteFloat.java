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
 * Converts floats to byte arrays and vice versa.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ByteFloat {

    /**
     * Hidden because pure static class.
     */
    private ByteFloat() {
        super();
    }

    public static float floatFromBytes(final byte[] bytes) {
        return floatFromBits(ByteInt.intFromBytes(bytes));
    }

    static float floatFromBits(final int bits) {
        return Float.intBitsToFloat(bits);
    }

    static int bitsFromFloat(final float f) {
        return Float.floatToRawIntBits(f);
    }

    public static byte[] bytesFromFloat(final float f) {
        return ByteInt.bytesFromInt(bitsFromFloat(f));
    }

}

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

package de.weltraumschaf.registermachine.convert;

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

    /**
     * First converts the bytes into a 32 bit integer representation of the float
     * and then converts this to a real float.
     *
     * @param bytes four bytes of the integer representation
     * @return 32 bit float
     */
    public static float floatFromBytes(final byte[] bytes) {
        return floatFromBits(ByteInteger.intFromBytes(bytes));
    }

    /**
     * Converts a 32 bit integer representation of a float to a real float.
     *
     * @param bits 32 bit integer representation of float
     * @return 32 bit float
     */
    static float floatFromBits(final int bits) {
        return Float.intBitsToFloat(bits);
    }

    /**
     * Converts a float value to it's 32 bit integer representation.
     *
     * @param f float to convert
     * @return 32 bit integer representation
     */
    static int bitsFromFloat(final float f) {
        return Float.floatToRawIntBits(f);
    }

    /**
     * Converts a float to four bytes.
     *
     * First converts the float to a 32 bit integer representation which will then
     * converted to four bytes.
     *
     * @param f float to convert
     * @return four bytes
     */
    public static byte[] bytesFromFloat(final float f) {
        return ByteInteger.bytesFromInt(bitsFromFloat(f));
    }

}

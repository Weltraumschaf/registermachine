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
 * Converts integers to array of bytes and vice versa.
 *
 * Converts big endian.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ByteInteger {

    /** How much bits to shift 2nd byte. */
    private static final int SECOND_BYTE_SHIFT = 8;
    /** How much bits to shift 3rd byte. */
    private static final int THIRD_BYTE_SHIFT = 16;
    /** How much bits to shift 4th byte. */
    private static final int FOURTH_BYTE_SHIFT = 24;

    private static final int ONE_BYTE_MASK = 0xFF;
    private static final int TWO_BYTE_MASK = 0xFFFF;
    private static final int THREE_BYTE_MASK = 0xFFFFFF;
    private static final int FOUR_BYTE_MASK = 0xFFFFFFFF;

    static final int FIRST_BYTE = 0;
    static final int SECOND_BYTE = 1;
    static final int THIRD_BYTE = 2;
    static final int FOURTH_BYTE = 3;

    private static final int BYTES_SHORT = 2;
    private static final int BYTES_INT = 4;

    private static final String BYTE_COUNT_ERROR_FMT = "Need %d bytes exactly!";


    /**
     * Hide constructor for pure static class.
     */
    private ByteInteger() {
        super();
    }

    /**
     * Converts 16 bit short to two byte arrays.
     *
     * @param value short to convert
     * @return array with 2 bytes
     */
    public static byte[] bytesFromShort(final short value) {
        final byte[] bytes = new byte[BYTES_SHORT];
        bytes[FIRST_BYTE]  = (byte) value;
        bytes[SECOND_BYTE] = (byte) (value >> SECOND_BYTE_SHIFT);
        return bytes;
    }

    /**
     * Converts array of two bytes into 16 bit short integer.
     *
     * @param bytes array with exactly 2 bytes
     * @return short integer
     * @throws IllegalArgumentException if, the passed in array does not have exactly 2 bytes
     */
    public static short shortFromBytes(final byte[] bytes) {
        if (bytes.length != BYTES_SHORT) {
            throw new IllegalArgumentException(String.format(BYTE_COUNT_ERROR_FMT, BYTES_SHORT));
        }

        short value = (short) (bytes[0]                         & ONE_BYTE_MASK);
        value      |= (bytes[SECOND_BYTE] << SECOND_BYTE_SHIFT) & TWO_BYTE_MASK;
        return value;
    }

    /**
     * Converts 32 bit short to four byte arrays.
     *
     * @param value int to convert
     * @return array with 4 bytes
     */
    public static byte[] bytesFromInt(final int value) {
        final byte[] bytes = new byte[BYTES_INT];
        bytes[FIRST_BYTE]  = (byte) value;
        bytes[SECOND_BYTE] = (byte) (value >> SECOND_BYTE_SHIFT);
        bytes[THIRD_BYTE]  = (byte) (value >> THIRD_BYTE_SHIFT);
        bytes[FOURTH_BYTE] = (byte) (value >> FOURTH_BYTE_SHIFT);
        return bytes;
    }

    /**
     * Converts array of four bytes into 32 bit short integer.
     *
     * @param bytes array with exactly 4 bytes
     * @return int integer
     * @throws IllegalArgumentException if, the passed in array does not have exactly 4 bytes
     */
    public static int intFromBytes(final byte[] bytes) {
        if (bytes.length != BYTES_INT) {
            throw new IllegalArgumentException(String.format(BYTE_COUNT_ERROR_FMT, BYTES_INT));
        }

        int value = bytes[FIRST_BYTE]                         & ONE_BYTE_MASK;
        value    |= (bytes[SECOND_BYTE] << SECOND_BYTE_SHIFT) & TWO_BYTE_MASK;
        value    |= (bytes[THIRD_BYTE]  << THIRD_BYTE_SHIFT)  & THREE_BYTE_MASK;
        value    |= (bytes[FOURTH_BYTE] << FOURTH_BYTE_SHIFT) & FOUR_BYTE_MASK;
        return value;
    }

}

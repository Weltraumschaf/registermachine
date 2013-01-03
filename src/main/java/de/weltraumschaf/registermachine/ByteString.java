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

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Converts UTF-8 strings to byte arrays and vice versa.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ByteString {

    /**
     * Used charset.
     */
    private static final Charset UTF8_CHARSET = Charset.forName(App.ENCODING);

    /**
     * Hidden because pure static class.
     */
    private ByteString() {
        super();
    }

    /**
     * Get character for bytes.
     *
     * @param bytes bytes converted
     * @return unicode character
     * @throws UnsupportedEncodingException if, UTF-8 is not supported
     */
    public static char charFromBytes(final byte[] bytes) throws UnsupportedEncodingException {
        return stringFromBytes(bytes).charAt(0);
    }

    /**
     * Get bytes for character.
     *
     * @param c character converted
     * @return unicode bytes
     * @throws UnsupportedEncodingException if, UTF-8 is not supported
     */
    public static byte[] bytesFromChar(final char c) throws UnsupportedEncodingException {
        return bytesFromString(String.valueOf(c));
    }

    /**
     * Get string for bytes.
     *
     * @param bytes bytes converted
     * @return unicode string
     * @throws UnsupportedEncodingException if, UTF-8 is not supported
     */
    public static String stringFromBytes(final byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, UTF8_CHARSET);
    }

    /**
     * Get bytes for string.
     *
     * @param str string converted
     * @return unicode bytes
     * @throws UnsupportedEncodingException if, UTF-8 is not supported
     */
    public static byte[] bytesFromString(final String str) throws UnsupportedEncodingException {
        return str.getBytes(UTF8_CHARSET);
    }

}

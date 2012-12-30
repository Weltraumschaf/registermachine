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

package de.weltraumschaf.registermachine.asm;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class CharUtils {

    private CharUtils() {
        super();
    }

    static boolean isNumeric(final char c) {
        return StringUtils.isNumeric(String.valueOf(c));
    }

    static boolean isAlpha(final char c) {
        return StringUtils.isAlpha(String.valueOf(c));
    }

    static boolean isWhitespace(final char c) {
        return StringUtils.isWhitespace(String.valueOf(c));
    }
}

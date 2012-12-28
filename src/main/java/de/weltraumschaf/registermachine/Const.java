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

import java.util.Locale;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Const {

    static final String ENCODING = "UTF-8";
    static final Locale LOCALE = Locale.ENGLISH;

    static final byte BC_FST_HEADER_BYTE = (byte) 0xCA;
    static final byte BC_SND_HEADER_BYTE = (byte) 0x7E;
    static final short BC_CURRENT_VERSION = (short) 0x01;
}

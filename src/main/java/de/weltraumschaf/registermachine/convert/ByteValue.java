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

import de.weltraumschaf.registermachine.typing.Value;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ByteValue {

    private ByteValue() {
        super();
    }

    public static Value valueFromBytes(final byte[] bytes) {
        return null;
    }

    public static byte[] bytesFromValue(final Value value) {
        return new byte[0];
    }

}

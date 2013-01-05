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

import de.weltraumschaf.registermachine.bytecode.OpCode;

/**
 * Helper class.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 * @deprecated Will be removed.
 */
@Deprecated
public final class Foo {

    /** Hidden. */
    private Foo() {
        super();
    }

    /**
     * Takes integers and converts them to a string with hex bytes.
     *
     * @param args integer input
     * @return hex output
     */
    public static String hex(final int ... args) {
        final StringBuilder b = new StringBuilder();
        int i = 0;
        for (int arg : args) {
            if (i > 0) {
                b.append(", ");
            }

            ++i;
            b.append("0x").append(OpCode.toHex((byte) arg));
        }
        return b.toString();
    }

}

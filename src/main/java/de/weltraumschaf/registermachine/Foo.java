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
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Foo {

    public static final String hex(int ... args) {
        StringBuilder b = new StringBuilder();
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

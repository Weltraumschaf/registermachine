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
package de.weltraumschaf.registermachine.typing;

import de.weltraumschaf.registermachine.bytecode.OpCode;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Code {

    private final OpCode op;
    private final int[] args;

    public Code(OpCode op, int[] args) {
        super();
        this.op = op;
        this.args = args;
    }

}

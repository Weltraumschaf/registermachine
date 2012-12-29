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

package de.weltraumschaf.registermachine.vm;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Pools {

    private Pools() {
        super();
    }

    static Pool newVariablePool() {
        return new VariablePool();
    }

    static Pool newConstantPool() {
        return new ConstantPool();
    }
}

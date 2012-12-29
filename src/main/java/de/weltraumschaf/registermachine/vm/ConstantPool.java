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

import de.weltraumschaf.registermachine.typing.Value;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ConstantPool extends VariablePool {

    @Override
    public final void assign(final Value v) {
        if (lookup(v)) {
            throw new IllegalArgumentException(String.format("Value %s already defined as constant!", v.toString()));
        }

        super.assign(v);
    }

}

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
 * Pool to store distinct constants values.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ConstantPool extends VariablePool {

    /**
     * Assign a value to the current index.
     *
     * @param value value to assign
     * CHECKSTYLE:OFF
     * @throws IllegalArgumentException if, value was already assigned
     * CHECKSTYLE:ON
     */
    @Override
    public final void assign(final Value value) {
        if (lookup(value)) {
            throw new IllegalArgumentException(String.format("Value %s already defined as constant!",
                                                             value.toString()));
        }

        super.assign(value);
    }

}

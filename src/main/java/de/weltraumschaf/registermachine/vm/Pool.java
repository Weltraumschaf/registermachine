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

import de.weltraumschaf.registermachine.inter.Value;

/**
 * Pool to store values by indexes.
 *
 * Indexes are automatically associated to the value at assignment starting by 0.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Pool {

    /**
     * Assign a value to the current index.
     *
     * @param value value to assign
     */
    void assign(Value value);
    /**
     * Checks if value was assigned at given index.
     *
     * @param index must be greater equal 0
     * @return true if value was assigned, else false
     */
    boolean lookup(int index);
    /**
     * Retrieve value from given index.
     *
     * @param index must be greater equal 0
     * @return stored value
     */
    Value retrieve(int index);

}

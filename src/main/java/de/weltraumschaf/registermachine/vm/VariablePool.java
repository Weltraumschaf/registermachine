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

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import de.weltraumschaf.registermachine.inter.Value;
import java.util.List;

/**
 * Pool to store variable values.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class VariablePool implements Pool {

    /**
     * Initial pool capacity.
     */
    private static final int DEFAULT_CAPACITY = 8;
    /**
     * Stores the values.
     */
    private final List<Value> values;

    /**
     * Initialize pool with a default capacity.
     */
    public VariablePool() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Dedicated constructor.
     *
     * @param size initial capacity
     */
    public VariablePool(final int size) {
        super();
        values = Lists.newArrayListWithCapacity(size);
    }

    @Override
    public void assign(final Value value) {
        values.add(value);
    }

    /**
     * Lookup if the given value already stored.
     *
     * @param value to lookup
     * @return true if value already assigned, else false
     */
    boolean lookup(final Value value) {
        return values.contains(value);
    }

    @Override
    public boolean lookup(final int index) {
        try {
            values.get(index);
            return true;
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }

    @Override
    public Value retrieve(final int index) {
        if (lookup(index)) {
            return values.get(index);
        }
        throw new IllegalArgumentException(String.format("Does not have value at index %d!", index));
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("values", values).toString();
    }

}

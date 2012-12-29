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
import de.weltraumschaf.registermachine.typing.Value;
import java.util.List;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class VariablePool implements Pool {
    private static final int DEFAULT_CAPACITY = 0;

    private final List<Value> values;

    public VariablePool() {
        this(DEFAULT_CAPACITY);
    }

    public VariablePool(final int size) {
        super();
        values = Lists.newArrayListWithCapacity(size);
    }

    @Override
    public void assign(final Value v) {
        values.add(v);
    }

    public boolean lookup(final Value v) {
        return values.contains(v);
    }

    @Override
    public boolean lookup(final int index) {
        return values.contains(index);
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

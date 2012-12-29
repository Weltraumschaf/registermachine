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

import com.google.common.collect.Maps;
import de.weltraumschaf.registermachine.typing.Value;
import java.util.Map;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class Register {

    private final Map<Integer, Value> store = Maps.newHashMap();

    void set(final int r, final Value v) {
        if (r < 0) {
            throw new IllegalArgumentException("Register address must be greater equal 0! Given: " + r);
        }
        store.put(Integer.valueOf(r), v);
    }

    Value get(final int r) {
        if (r < 0) {
            throw new IllegalArgumentException("Register address must be greater equal 0! Given: " + r);
        }

        if (store.containsKey(Integer.valueOf(r))) {
            return store.get(Integer.valueOf(r));
        }

        return Value.getNil();
    }

    @Override
    public String toString() {
        return store.toString();
    }

}

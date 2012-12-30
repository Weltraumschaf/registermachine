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

import com.google.common.base.Objects;
import de.weltraumschaf.registermachine.bytecode.OpCode;
import java.util.List;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Code {

    private final OpCode op;
    private final List<Integer> args;

    public Code(OpCode op, List<Integer> args) {
        super();
        this.op = op;
        this.args = args;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(op, args);
    }

    @Override
    public boolean equals(final Object obj) {
        if (! (obj instanceof Code)) {
            return false;
        }

        final Code other = (Code) obj;

        return Objects.equal(op, other.op) && Objects.equal(args, other.args);
    }
}

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

package de.weltraumschaf.registermachine.inter;

/**
 * Represents a parsed variable.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class VarNode extends ValueNode {

    /**
     * Hidden to enforce usage of {@link #newVarNode(java.lang.String, de.weltraumschaf.registermachine.inter.Value)
     * factory method}.
     *
     * @param name name of value.
     * @param value typed value.
     */
    private VarNode(final String name, final Value value) {
        super(name, value, Type.VAR);
    }

    /**
     * Factory method.
     *
     * @param name name of value.
     * @param value typed value.
     * @return new instance
     */
    static VarNode newVarNode(final String name, final Value value) {
        return new VarNode(name, value);
    }

}

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
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Nodes {

    public Nodes() {
        super();
    }

    public NopNode newNopNode() {
        return NopNode.newNopNode();
    }

    public VarNode newVarNode(final String name, final Value value) {
        return VarNode.newVarNode(name, value);
    }

    public FunctionNode newFunctionNode() {
        return FunctionNode.newFunctionNode();
    }

}

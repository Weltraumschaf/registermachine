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
 * Factory to create AST nodes.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Nodes {

    /**
     * Default constructor.
     */
    public Nodes() {
        super();
    }

    /**
     * Creates a NOP node.
     *
     * @return new instance
     */
    public NopNode newNopNode() {
        return NopNode.newNopNode();
    }

    /**
     * Factory method.
     *
     * @param name name of value.
     * @param value typed value.
     * @return new instance
     */
    public VarNode newVarNode(final String name, final Value value) {
        return VarNode.newVarNode(name, value);
    }

    /**
     * Factory method.
     *
     * @param name name of value.
     * @param value typed value.
     * @return new instance
     */
    public ConstNode newConstNode(final String name, Value value) {
        return ConstNode.newConstNode(name, value);
    }

    /**
     * Factory method.
     *
     * @return new instance
     */
    public FunctionNode newFunctionNode() {
        return FunctionNode.newFunctionNode();
    }

}

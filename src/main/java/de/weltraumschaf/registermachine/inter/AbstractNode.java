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
 * Generic functionality for all nodes.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class AbstractNode implements AstNode {

    /**
     * Type of node.
     */
    private final Type type;

    /**
     * Initializes the node with a type.
     *
     * @param type node type
     */
    public AbstractNode(final Type type) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.toString();
    }

}

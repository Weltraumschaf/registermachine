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
 * Nodes of the abstract syntax tree.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface AstNode {

    /**
     * Type of a node.
     */
    public enum Type {
        /**
         * Empty node.
         *
         * Mostly used if empty source was parsed.
         */
        NOP,
        /**
         * Variable node.
         */
        VAR,
        FUNCTION;
    }

    /**
     * Get the node type.
     *
     * @return the type of the node
     */
    Type getType();

}

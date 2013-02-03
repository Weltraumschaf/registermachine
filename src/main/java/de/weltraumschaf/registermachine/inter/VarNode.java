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
public class VarNode extends AbstractNode {

    /**
     * Name of the variable.
     */
    private final String name;
    /**
     * Value of the variable.
     */
    private final Value value;

    private VarNode(final String name, final Value value) {
        super(AstNode.Type.VAR);
        this.name = name;
        this.value = value;
    }

    public static AstNode newVarNode(final String name, final Value value) {
        return new VarNode(name, value);
    }

    public String getName() {
        return name;
    }

    public Value getValue() {
        return value;
    }

}

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

package de.weltraumschaf.registermachine.ast;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class VariableNode extends AbstractNode {

    private SymbolNode name;
    private ExpressionNode value;

    void setName(final SymbolNode name) {
        this.name = name;
    }

    void setValue(final ExpressionNode value) {
        this.value = value;
    }

}

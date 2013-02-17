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
public class SymbolNode extends AbstractNode {

    private final String id;

    public SymbolNode(final String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof SymbolNode)) {
            return false;
        }

        final SymbolNode other = (SymbolNode) obj;
        return id.equals(other.id);
    }

}

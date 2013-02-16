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

import de.weltraumschaf.registermachine.inter.Value;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ValueNode extends AbstractNode {

    private Value value;

    void setValue(final Value v) {
        value = v;
    }
}

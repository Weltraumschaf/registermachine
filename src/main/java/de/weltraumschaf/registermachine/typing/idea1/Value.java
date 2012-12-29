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

package de.weltraumschaf.registermachine.typing.idea1;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Value {

    /**
     * Nil, String, Table or Function.
     */
    private BasicType<?> basicType;
    private boolean booleanType;
    private int intType;
    private float floatType;

    public Value(final BasicType<?> basicType) {
        this.basicType = basicType;
    }

    public BasicType<?> getBasicType() {
        return basicType;
    }

    public boolean getBooleanType() {
        return booleanType;
    }

    public int getIntType() {
        return intType;
    }

    public float getFloatType() {
        return floatType;
    }

}

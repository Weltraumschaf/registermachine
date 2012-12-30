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
package de.weltraumschaf.registermachine.typing;

import com.google.common.base.Objects;

/**
 * Imutable representation of a value.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Value {

    /**
     * Universal NIL.
     */
    private static final Value NIL = new Value();
    /**
     * Universal TRUE.
     */
    private static final Value TRUE = new Value(true);
    /**
     * Universal FALSE.
     */
    private static final Value FALSE = new Value(false);
    /**
     * Type of value.
     */
    private final Type type;
    private final int integerValue;
    private final float floatValue;
    private final boolean booleanValue;

    private Value() {
        this(Type.NIL, 0, (float) 0.0, false);
    }

    private Value(final int integerValue) {
        this(Type.INTEGER, integerValue, integerToFloat(integerValue), integerTwoBoolean(integerValue));
    }

    private Value(final float floatValue) {
        this(Type.FLOAT, floatToInteger(floatValue), floatValue, floatToBoolean(floatValue));
    }

    private Value(final boolean booleanValue) {
        this(Type.BOOLEAN, booleanToInteger(booleanValue), booleanTwoFloat(booleanValue), booleanValue);
    }

    private Value(Type type, int integerValue, float floatValue, boolean booleanValue) {
        super();
        this.type = type;
        this.integerValue = integerValue;
        this.floatValue = floatValue;
        this.booleanValue = booleanValue;
    }

    public Type getType() {
        return type;
    }

    public int getIntegerValue() {
        return integerValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public boolean getBooleanValue() {
        return booleanValue;
    }

    public static Value getNil() {
        return NIL;
    }

    public static Value getTrue() {
        return TRUE;
    }

    public static Value getFalse() {
        return FALSE;
    }

    @Override
    public String toString() {
        switch (type) {
            case INTEGER:
                return String.valueOf(getIntegerValue());
            case FLOAT:
                return String.valueOf(getFloatValue());
            case BOOLEAN:
                return String.valueOf(getBooleanValue());
            case NIL:
                return "NIL";
            default:
                return "UNKNOWN";

        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, booleanValue, floatValue, integerValue);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Value)) {
            return false;
        }

        final Value other = (Value) obj;
        return Objects.equal(type, other.type)
                && Objects.equal(booleanValue, other.booleanValue)
                && Objects.equal(floatValue, other.floatValue)
                && Objects.equal(integerValue, other.integerValue);
    }

    static boolean integerTwoBoolean(final int in) {
        return in != 0;
    }

    static float integerToFloat(final int in) {
        return (float) in;
    }

    static int floatToInteger(final float in) {
        return (int) in;
    }

    static boolean floatToBoolean(final float in) {
        return Math.abs(in - 0.0) > 0.00001;
    }

    static int booleanToInteger(final boolean in) {
        return in ? 1 : 0;
    }

    static float booleanTwoFloat(final boolean in) {
        return in ? (float) 1.0 : (float) 0.0;
    }

    public static Value valueOf(final boolean value) {
        return new Value(value);
    }

    public static Value valueOf(final int value) {
        return new Value(value);
    }

    public static Value valueOf(final float value) {
        return new Value(value);
    }

    Value castTo(Type t) {
        if (t == type) {
            return this;
        }

        return new Value(t, integerValue, floatValue, booleanValue);
    }

    Value castToBoolean() {
        return castTo(Type.BOOLEAN);
    }

    Value castToInteger() {
        return castTo(Type.INTEGER);
    }

    Value castToFloValue() {
        return castTo(Type.FLOAT);
    }
}

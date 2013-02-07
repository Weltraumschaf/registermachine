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

import com.google.common.base.Objects;

/**
 * Immutable representation of a value.
 *
 * Holds a boxed representation of all native types.
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
     * Used as delta for float comparing.
     *
     * Two floats are considered same, if the absolute value of their difference is less than the delta.
     */
    private static final double FLOAT_COMPARE_DELTA = 0.00001f;
    /**
     * Type of value.
     */
    private final Type type;
    /**
     * Integer representation.
     */
    private final int integerValue;
    /**
     * Float representation.
     */
    private final float floatValue;
    /**
     * Boolean representation.
     */
    private final boolean booleanValue;
    /**
     * String representation.
     */
    private final String stringValue;

    /**
     * Default constructor for NIL types.
     */
    private Value() {
        this(Type.NIL, 0, (float) 0.0, false, "");
    }

    /**
     * Default constructor for integer types.
     *
     * @param integerValue the value to represent
     */
    private Value(final int integerValue) {
        this(Type.INTEGER,
                integerValue,
                integerToFloat(integerValue),
                integerToBoolean(integerValue),
                String.valueOf(integerValue));
    }

    /**
     * Default constructor for float types.
     *
     * @param floatValue the value to represent
     */
    private Value(final float floatValue) {
        this(Type.FLOAT,
                floatToInteger(floatValue),
                floatValue,
                floatToBoolean(floatValue),
                String.valueOf(floatValue));
    }

    /**
     * Default constructor for boolean types.
     *
     * @param booleanValue the value to represent
     */
    private Value(final boolean booleanValue) {
        this(Type.BOOLEAN,
                booleanToInteger(booleanValue),
                booleanTwoFloat(booleanValue),
                booleanValue,
                String.valueOf(booleanValue));
    }

    private Value(final String stringValue) {
        this(Type.STRING,
                str2int(stringValue),
                (float) str2int(stringValue),
                str2int(stringValue) == 0 ? false : true,
                stringValue);
    }

    /**
     * Dedicated constructor.
     *
     * @param type type of boxed value
     * @param integerValue the boxed integer representation of the value
     * @param floatValue the boxed float representation of the value
     * @param booleanValue the boxed boolean representation of the value
     * @param stringValue  the boxed string representation of the value
     */
    private Value(final Type type, final int integerValue, final float floatValue, final boolean booleanValue,
            final String stringValue) {
        super();
        this.type = type;
        this.integerValue = integerValue;
        this.floatValue = floatValue;
        this.booleanValue = booleanValue;
        this.stringValue = stringValue;
    }

    /**
     * Converts a string to an integer.
     *
     * @param in string to convert
     * @return 0 if the string is null or empty, 1 else
     */
    private static int str2int(final String in) {
        if (null == in) {
            return 0;
        }

        return in.isEmpty() ? 0 : 1;
    }

    /**
     * Get original type of value.
     *
     * @return type of value
     */
    public Type getType() {
        return type;
    }

    /**
     * Get the integer representation of the value.
     *
     * @return the value casted to integer
     */
    public int getIntegerValue() {
        return integerValue;
    }

    /**
     * Get the float representation of the value.
     *
     * @return the value casted to float
     */
    public float getFloatValue() {
        return floatValue;
    }

    /**
     * Get the boolean representation of the value.
     *
     * @return the value casted to boolean
     */
    public boolean getBooleanValue() {
        return booleanValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    /**
     * Static factory to get a NIL value object.
     *
     * @return shared instance
     */
    public static Value getNil() {
        return NIL;
    }

    /**
     * Static factory to get a boolean TRUE value object.
     *
     * @return shared instance
     */
    public static Value getTrue() {
        return TRUE;
    }

    /**
     * Static factory to get a boolean FALSE value object.
     *
     * @return shared instance
     */
    public static Value getFalse() {
        return FALSE;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(type).append(": ");
        switch (type) {
            case INTEGER:
                buffer.append(String.valueOf(getIntegerValue()));
                break;
            case FLOAT:
                buffer.append(String.valueOf(getFloatValue()));
                break;
            case BOOLEAN:
                buffer.append(String.valueOf(getBooleanValue()));
                break;
            case STRING:
                buffer.append(stringValue);
                break;
            case NIL:
                buffer.append("NIL");
                break;
            default:
                buffer.append("UNKNOWN");
                break;
        }
        return buffer.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, booleanValue, floatValue, integerValue, stringValue);
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
                && Objects.equal(integerValue, other.integerValue)
                && Objects.equal(stringValue, other.stringValue);
    }

    /**
     * Calculates a boolean value from given integer.
     *
     * @param in the integer to convert
     * @return 0 will result in false, everything else in true
     */
    static boolean integerToBoolean(final int in) {
        return in != 0;
    }

    /**
     * Calculates a float value from given integer.
     *
     * @param in the integer to convert
     * @return value casted to float
     */
    static float integerToFloat(final int in) {
        return (float) in;
    }

    /**
     * Calculates a integer value from given float.
     *
     * @param in the float to convert
     * @return value casted to integer
     */
    static int floatToInteger(final float in) {
        return (int) in;
    }

    static boolean floatToBoolean(final float in) {
        return Math.abs(in - 0.0f) > FLOAT_COMPARE_DELTA;
    }

    static int booleanToInteger(final boolean in) {
        return in ? 1 : 0;
    }

    static float booleanTwoFloat(final boolean in) {
        return in ? (float) 1.0 : (float) 0.0;
    }

    /**
     * Factory to create boxed boolean values.
     *
     * @param value input value
     * @return return shared instances for TRUE or FALSE
     */
    public static Value valueOf(final boolean value) {
        if (value) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    /**
     * Factory to create boxed integer values.
     *
     * @param value input value
     * @return new instance
     */
    public static Value valueOf(final int value) {
        return new Value(value);
    }

    /**
     * Factory to create boxed float values.
     *
     * @param value input value
     * @return new instance
     */
    public static Value valueOf(final float value) {
        return new Value(value);
    }

    public static Value valueOf(final String value) {
        return new Value(value);
    }

    /**
     * Cast a value to an other type.
     *
     * If same type given as value has, the same object is returned.
     *
     * @param t used to cast value to
     * @return same value with a different type, different reference
     */
    Value castTo(final Type t) {
        if (t == type) { // NOPMD
            return this;
        }

        if (Type.BOOLEAN == t) {
            if (booleanValue) {
                return TRUE;
            } else {
                return FALSE;
            }
        } else if (Type.NIL == t) {
            return NIL;
        }

        return new Value(t, integerValue, floatValue, booleanValue, stringValue);
    }

    /**
     * Cast to boolean.
     *
     * @return shared instance of {@link #TRUE} or {@link #FALSE}
     */
    Value castToBoolean() {
        return castTo(Type.BOOLEAN);
    }

    /**
     * Cast to integer.
     *
     * @return new integer value, if different type
     */
    Value castToInteger() {
        return castTo(Type.INTEGER);
    }

    /**
     * Cast to float.
     *
     * @return new float value, if different type
     */
    Value castToFloValue() {
        return castTo(Type.FLOAT);
    }

    Value castToString() {
        return castTo(Type.STRING);
    }
}

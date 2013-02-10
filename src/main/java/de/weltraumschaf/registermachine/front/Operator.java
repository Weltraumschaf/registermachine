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
package de.weltraumschaf.registermachine.front;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * List of all reserved operands.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum Operator {

    LOGICAL_AND("&&"),
    LOGICAL_OR("||"),
    LOGICAL_NOT("!"),
    EQUAL("=="),
    NOT_EQUAL("!="),
    LESS_THAN("<"),
    LESS_EQUAL("<="),
    GREATER_THAN(">"),
    GREATER_EQUAL(">="),
    PLUS("+"),
    MINUS("-"),
    STAR("*"),
    SLASH("/"),
    MODULO("%");

    /**
     * Map to look up operator for a literal string.
     */
    private static final Map<String, Operator> LOOKUP = Maps.newHashMap();
    static {
        for (final Operator o : Operator.values()) {
            LOOKUP.put(o.getLiteral(), o);
        }
    }

    /**
     * Literal string of operand.
     */
    private final String literal;

    /**
     * Dedicated constructor.
     *
     * @param literal string of operator
     */
    Operator(final String literal) {
        this.literal = literal;
    }

    /**
     * Get the literal operand string.
     *
     * @return the literal string
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Determines if a literal string is a operator.
     *
     * @param str literal string to test.
     * @return {@code true} if passed in string is a operator, else {@code false}
     */
    public static boolean isOperator(final String str) {
        return LOOKUP.containsKey(str);
    }

    /**
     * Get the keyword for a literal operator string.
     *
     * @param str the literal operator string
     * @return the associated enum
     * CHECKSTYLE:OFF
     * @throws IllegalArgumentException if passed in string is not a operator
     * CHECKSTYLE:ON
     */
    public static Operator forValue(final String str) {
        if (isOperator(str)) {
            return LOOKUP.get(str);
        }
        throw new IllegalArgumentException(String.format("Literal string '%s' is not an operator!", str));
    }
}

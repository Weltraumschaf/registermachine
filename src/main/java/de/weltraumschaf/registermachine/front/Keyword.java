/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a beer in return.
 *
 */
package de.weltraumschaf.registermachine.front;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * List of all reserved keywords.
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
public enum Keyword {

    /**
     * Variable declaration keyword.
     */
    VAR("var"),
    /**
     * Keyword declaration keyword.
     */
    CONST("const"),
    /**
     * Function declaration keyword.
     */
    FUNCTION("function"),
    /**
     * Function return keyword.
     */
    RETURN("return"),
    /**
     * For loop keyword.
     */
    FOR("for"),
    /**
     * If statement keyword.
     */
    IF("if"),
    /**
     * Else statement keyword.
     */
    ELSE("else"),
    /**
     * Switch statement keyword.
     */
    SWITCH("switch"),
    /**
     * Case statement keyword.
     */
    CASE("case"),
    /**
     * Break statement keyword.
     */
    BREAK("break"),
    /**
     * Continue statement keyword.
     */
    CONTINUE("continue");

    /**
     * Map to look up keyword for a literal string.
     */
    private static final Map<String, Keyword> LOOKUP = Maps.newHashMap();
    static {
        for (final Keyword k : Keyword.values()) {
            LOOKUP.put(k.getLiteral(), k);
        }
    }

    /**
     * Literal string of keyword.
     */
    private final String literal;

    /**
     * Dedicated constructor.
     *
     * @param literal string of keyword
     */
    private Keyword(String literal) {
        this.literal = literal;
    }

    /**
     * Get the literal keyword string.
     *
     * @return the literal string
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Determines if a literal string is a keyword.
     *
     * @param str literal string to test.
     * @return {@code true} if passed in string is a keyword, else {@code false}
     */
    public static boolean isKeyword(final String str) {
        return LOOKUP.containsKey(str);
    }

    /**
     * Get the keyword for a literal keyword string.
     *
     * @param str the literal keyword string
     * @return the associated enum
     * CHECKSTYLE:OFF
     * @throws IllegalArgumentException if passed in string is not a keyword
     * CHECKSTYLE:ON
     */
    public static Keyword forValue(final String str) {
        if (isKeyword(str)) {
            return LOOKUP.get(str);
        }
        throw new IllegalArgumentException(String.format("Literal string '%s' is not a keyword!", str));
    }

}

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

package de.weltraumschaf.registermachine.asm;

/**
 * Assembler token.
 *
 * Object is immutable.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class Token {

    /** Token type. */
    private final TokenType type;
    /** Token raw value. */
    private final String value;

    /**
     * Creates a token.
     *
     * @param type the token type
     * @param value the tokens raw value, beginning and ending " of strings should be removed
     */
    public Token(final TokenType type, final String value) {
        super();
        this.type = type;
        this.value = value;
    }

    /**
     * Get the token type.
     *
     * @return tokens type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Get the token value.
     *
     * @return the tokens raw value.
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s=%s", type, value);
    }

}

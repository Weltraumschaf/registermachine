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
 * Represents a string as stream accessible character by character.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class CharStream {

    /** Indicates end of line. */
    static final char EOL = (char) 0;

    /**
     * Line number in the source.
     *
     * By default this may be -1.
     */
    private final int lineNumber;
    /**
     * String representing the line.
     */
    private final String str;
    /**
     * Current character position.
     */
    private int index;

    /**
     * Initializes {@link #lineNumber} with -1.
     *
     * @param str representing the line
     */
    CharStream(final String str) {
        this(str, -1);
    }

    /**
     * Dedicated constructor.
     *
     * @param str representing the line
     * @param lineNumber line number in the source string
     */
    CharStream(final String str, final int lineNumber) {
        super();
        this.str = str;
        this.lineNumber = lineNumber;
    }

    boolean isEmpty() {
        return str.isEmpty();
    }

    char getCurrentChar() {
        try {
            return str.charAt(index);
        } catch (IndexOutOfBoundsException ex) {
            return EOL;
        }
    }

    void nextChar() {
        if (hasNextChar()) {
            ++index;
        }
    }

    char peekChar() {
        try {
            return str.charAt(index + 1);
        } catch (IndexOutOfBoundsException ex) {
            return EOL;
        }
    }

    boolean hasNextChar() {
        return index < str.length();
    }

    int getIndex() {
        return index;
    }

    int getLineNumber() {
        return lineNumber;
    }

}

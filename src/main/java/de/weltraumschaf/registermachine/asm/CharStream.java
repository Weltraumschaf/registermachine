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
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class CharStream {

    private final int lineNumber;
    private final String str;
    private int index;

    CharStream(final String str) {
        this(str, -1);
    }

    CharStream(final String str, final int lineNumber) {
        super();
        this.str = str;
        this.lineNumber = lineNumber;
    }

    boolean isEmpty() {
        return str.isEmpty();
    }

    char getCurrentChar() {
        return str.charAt(index);
    }

    void nextChar() {
        if (hasNextChar()) {
            ++index;
        }
    }

    char peekChar() {
        return str.charAt(index + 1);
    }

    boolean hasNextChar() {
        return index < str.length() - 1;
    }

    int getIndex() {
        return index;
    }

    int getLineNumber() {
        return lineNumber;
    }

}

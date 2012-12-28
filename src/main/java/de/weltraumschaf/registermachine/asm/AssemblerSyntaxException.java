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
 * Syntax error in assembly code.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class AssemblerSyntaxException extends Exception {

    /**
     * Sets the line to -1.
     * 
     * @param syntaxError the human readable error message
     */
    public AssemblerSyntaxException(final String syntaxError) {
        this(syntaxError, -1);
    }

    /**
     * Designated constructor.
     *
     * @param syntaxError the human readable error message
     * @param line line in source asm where the error is
     */
    public AssemblerSyntaxException(final String syntaxError, final int line) {
        super(String.format("Syntax error on line %d: %s", line, syntaxError));
    }

}

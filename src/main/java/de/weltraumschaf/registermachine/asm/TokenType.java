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
enum TokenType {

    /**
     * Mnemonics, true, false and nil.
     */
    LITERAL,
    /**
     * Everything between "...".
     */
    STRING,
    /**
     * Numbers with a dot in it.
     */
    FLOAT,
    /**
     * Numbers without a dot in it.
     */
    INTEGER,
    /**
     * Starts with # followed by a integer.
     */
    REGISTER,
    /**
     * Evereything preceeded by ;
     */
    COMMENT;

}

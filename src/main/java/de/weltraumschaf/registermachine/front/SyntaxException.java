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

import de.weltraumschaf.commons.token.Position;

/**
 * Exception to signal syntax errors.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SyntaxException extends RuntimeException {

    /**
	 * Serial version.
	 */
	private static final long serialVersionUID = -504793219747754628L;
	
	/**
     * Position of the error.
     *
     * May be null.
     */
    private final Position pos;

    /**
     * Initializes {@link #pos} with {2code null}.
     *
     * @param reason reason for the syntax error
     */
    SyntaxException(final String reason) {
        this(reason, null);
    }

    /**
     * Dedicated constructor.
     *
     * @param reason reason for the syntax error
     * @param pos source code position of the syntax error
     */
    SyntaxException(final String reason, final Position pos) {
        super(reason);
        this.pos = pos;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("Syntax error: ").append(getMessage()).append(" At position ");

        if (null == pos) {
            buffer.append("unknown");
        } else {
            buffer.append(pos);
        }

        return buffer.append('.').toString();
    }

}

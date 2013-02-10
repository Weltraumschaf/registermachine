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
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SyntaxExceptionTest {

    @Test
    public void toString_withPosition() {
        final Position pos = new Position(1, 2, "/foo/bar/baz");
        final SyntaxException sut = new SyntaxException("Foo bar baz!", pos);
        assertThat(sut.toString(), is("Syntax error: Foo bar baz! At position /foo/bar/baz (1, 2)."));
    }

    @Test
    public void toString_withouPosition() {
        final SyntaxException sut = new SyntaxException("Foo bar baz!");
        assertThat(sut.toString(), is("Syntax error: Foo bar baz! At position unknown."));
    }

}
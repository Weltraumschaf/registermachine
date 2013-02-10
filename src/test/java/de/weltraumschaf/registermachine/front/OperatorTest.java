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

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class OperatorTest {

    // CHECKSTYLE:OFF
    @Rule public ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON

    @Test
    public void isOperator() {
        assertTrue(Operator.isOperator("&&"));
        assertTrue(Operator.isOperator("||"));
        assertTrue(Operator.isOperator("=="));
        assertTrue(Operator.isOperator("!="));
        assertTrue(Operator.isOperator("<"));
        assertTrue(Operator.isOperator("<="));
        assertTrue(Operator.isOperator(">"));
        assertTrue(Operator.isOperator(">="));
        assertTrue(Operator.isOperator("+"));
        assertTrue(Operator.isOperator("-"));
        assertTrue(Operator.isOperator("*"));
        assertTrue(Operator.isOperator("/"));
        assertTrue(Operator.isOperator("%"));
        assertTrue(Operator.isOperator("!"));

        assertFalse(Operator.isOperator(""));
        assertFalse(Operator.isOperator("   "));
        assertFalse(Operator.isOperator("foobar"));
    }

    @Test
    public void forValue() {
        assertThat(Operator.forValue("&&"), is(Operator.LOGICAL_AND));
        assertThat(Operator.forValue("||"), is(Operator.LOGICAL_OR));
        assertThat(Operator.forValue("=="), is(Operator.EQUAL));
        assertThat(Operator.forValue("!="), is(Operator.NOT_EQUAL));
        assertThat(Operator.forValue("<"), is(Operator.LESS_THAN));
        assertThat(Operator.forValue("<="), is(Operator.LESS_EQUAL));
        assertThat(Operator.forValue(">"), is(Operator.GREATER_THAN));
        assertThat(Operator.forValue(">="), is(Operator.GREATER_EQUAL));
        assertThat(Operator.forValue("+"), is(Operator.PLUS));
        assertThat(Operator.forValue("-"), is(Operator.MINUS));
        assertThat(Operator.forValue("*"), is(Operator.STAR));
        assertThat(Operator.forValue("/"), is(Operator.SLASH));
        assertThat(Operator.forValue("%"), is(Operator.MODULO));
        assertThat(Operator.forValue("!"), is(Operator.LOGICAL_NOT));
    }

    @Test
    public void forValue_throwsExceptionOnEmptyInput() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Literal string '' is not an operator!");
        Operator.forValue("");
    }

    @Test
    public void forValue_throwsExceptionOnSpacesAsInput() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Literal string '   ' is not an operator!");
        Operator.forValue("   ");
    }

    @Test
    public void forValue_throwsExceptionOnArbitraryStringInput() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Literal string 'foobar' is not an operator!");
        Operator.forValue("foobar");
    }

}
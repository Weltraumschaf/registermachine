/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.weltraumschaf.registermachine.front;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
public class KeywordTest {

    @Rule public ExpectedException thrown= ExpectedException.none();

    @Test
    public void isKeyword() {
        assertTrue(Keyword.isKeyword("var"));
        assertTrue(Keyword.isKeyword("const"));
        assertTrue(Keyword.isKeyword("function"));
        assertTrue(Keyword.isKeyword("return"));
        assertTrue(Keyword.isKeyword("for"));
        assertTrue(Keyword.isKeyword("if"));
        assertTrue(Keyword.isKeyword("else"));
        assertTrue(Keyword.isKeyword("switch"));
        assertTrue(Keyword.isKeyword("case"));
        assertTrue(Keyword.isKeyword("break"));
        assertTrue(Keyword.isKeyword("continue"));

        assertFalse(Keyword.isKeyword(""));
        assertFalse(Keyword.isKeyword("   "));
        assertFalse(Keyword.isKeyword("foobar"));
    }

    @Test
    public void forValue() {
        assertThat(Keyword.forValue("var"), is(Keyword.VAR));
        assertThat(Keyword.forValue("const"), is(Keyword.CONST));
        assertThat(Keyword.forValue("function"), is(Keyword.FUNCTION));
        assertThat(Keyword.forValue("return"), is(Keyword.RETURN));
        assertThat(Keyword.forValue("for"), is(Keyword.FOR));
        assertThat(Keyword.forValue("if"), is(Keyword.IF));
        assertThat(Keyword.forValue("else"), is(Keyword.ELSE));
        assertThat(Keyword.forValue("switch"), is(Keyword.SWITCH));
        assertThat(Keyword.forValue("case"), is(Keyword.CASE));
        assertThat(Keyword.forValue("break"), is(Keyword.BREAK));
        assertThat(Keyword.forValue("continue"), is(Keyword.CONTINUE));
    }

    @Test
    public void forValue_throwsExceptionOnEmptyInput() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Literal string '' is not a keyword!");
        Keyword.forValue("");
    }

    @Test
    public void forValue_throwsExceptionOnSpacesAsInput() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Literal string '   ' is not a keyword!");
        Keyword.forValue("   ");
    }

    @Test 
    public void forValue_throwsExceptionOnArbitraryStringInput() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Literal string 'foobar' is not a keyword!");
        Keyword.forValue("foobar");
    }
 }

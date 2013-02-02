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

    @Test @Ignore
    public void forValue() {
        assertThat(Keyword.valueOf("var"), is(Keyword.VAR));
        assertThat(Keyword.valueOf("const"), is(Keyword.CONST));
        assertThat(Keyword.valueOf("function"), is(Keyword.FUNCTION));
        assertThat(Keyword.valueOf("return"), is(Keyword.RETURN));
        assertThat(Keyword.valueOf("for"), is(Keyword.FOR));
        assertThat(Keyword.valueOf("if"), is(Keyword.IF));
        assertThat(Keyword.valueOf("else"), is(Keyword.ELSE));
        assertThat(Keyword.valueOf("switch"), is(Keyword.SWITCH));
        assertThat(Keyword.valueOf("case"), is(Keyword.CASE));
        assertThat(Keyword.valueOf("break"), is(Keyword.BREAK));
        assertThat(Keyword.valueOf("continue"), is(Keyword.CONTINUE));
    }

    @Test @Ignore
    public void forValue_throwsExceptionOnEmptyInput() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Literal string '' is not a keyword!");
        Keyword.valueOf("");
    }

    @Test @Ignore
    public void forValue_throwsExceptionOnSpacesAsInput() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Literal string '   ' is not a keyword!");
        Keyword.valueOf("   ");
    }

    @Test @Ignore
    public void forValue_throwsExceptionOnArbitraryStringInput() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Literal string 'foobar' is not a keyword!");
        Keyword.valueOf("foobar");
    }
 }

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

package de.weltraumschaf.registermachine.instructionset;

import de.weltraumschaf.registermachine.instr.Iload;
import de.weltraumschaf.registermachine.vm.RuntimeConfiguration;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class IloadTest {

    @Test
    public void createILoadWithReference() {
        final Iload sut = new Iload(23, "#1234");
        assertThat(sut.getRegister(), is(23));
        assertThat(sut.getAddress(), is(1234));
        assertThat(sut.getValue(), is((byte)0));
    }

    @Test
    public void createILoadWithByteValue() {
        final Iload sut = new Iload(23, (byte)42);
        assertThat(sut.getRegister(), is(23));
        assertThat(sut.getAddress(), is(-1));
        assertThat(sut.getValue(), is((byte)42));
    }

    @Test
    public void loadsReferencedValue_isTrueOnAddress() {
        final Iload sut = new Iload(23, "#1234");
        assertThat(sut.loadsReferencedValue(), is(true));
    }

    @Test
    public void loadsReferencedValue_isFalseOnByte() {
        final Iload sut = new Iload(23, (byte)42);
        assertThat(sut.loadsReferencedValue(), is(false));
    }

    @Test
    public void evaluate_withReference() {
        final Iload sut = new Iload(2, "#34");
        final RuntimeConfiguration conf = new RuntimeConfiguration();
        final byte[] expected = new byte[2];
        expected[0] = 42;
        expected[1] = 23;
        conf.getScope().setAssign(34, expected);
        sut.evaluate(conf);
        assertThat(conf.getRegister(2), is(expected));
    }

    @Test
    public void evaluate_withByteValue() {
        final Iload sut = new Iload(3, (byte)42);
        final RuntimeConfiguration conf = new RuntimeConfiguration();
        sut.evaluate(conf);
        final byte[] expected = new byte[1];
        expected[0] = 42;
        assertThat(conf.getRegister(3), is(expected));
    }

}
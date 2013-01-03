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
package de.weltraumschaf.registermachine.convert;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ByteFloatTest {

    @Test
    public void floatFromBytes() {
        assertThat(ByteFloat.floatFromBytes(new byte[]{86, 14, 73, 64}),
                   is(3.1415f));
        assertThat(ByteFloat.floatFromBytes(new byte[]{86, 14, 73, 64}),
                   is(ByteFloat.floatFromBytes(new byte[]{86, 14, 73, 64})));
    }

    @Test
    public void floatFromBits() {
        assertThat(ByteFloat.floatFromBits(1078529622), is(3.1415f));
        assertThat(ByteFloat.floatFromBits(1078529622), is(ByteFloat.floatFromBits(1078529622)));
    }

    @Test
    public void bitsFromFloat() {
        assertThat(ByteFloat.bitsFromFloat(3.1415f), is(1078529622));
        assertThat(ByteFloat.bitsFromFloat(3.1415f), is(ByteFloat.bitsFromFloat(3.1415f)));
    }

    @Test
    public void bytesFromFloat() {
        assertThat(ByteFloat.bytesFromFloat(3.1415f),
                is(ByteFloat.bytesFromFloat(3.1415f)));
        assertThat(ByteFloat.bytesFromFloat(3.1415f),
                is(new byte[]{86, 14, 73, 64}));
    }
}

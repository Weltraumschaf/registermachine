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

import de.weltraumschaf.registermachine.bytecode.ByteCodeStream;
import de.weltraumschaf.registermachine.inter.Type;
import de.weltraumschaf.registermachine.inter.Value;
import java.io.UnsupportedEncodingException;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ByteValueTest {

    private ByteCodeStream createStream(byte... bytes) {
        return new ByteCodeStream(bytes);
    }

    @Test
    public void valueFromBytes_Nil() throws UnsupportedEncodingException {
        final Value value = ByteValue.valueFromBytes(createStream((byte) 0x00));
        assertThat(value.getType(), is(Type.NIL));
        assertSame(value, Value.getNil());
    }

    @Test
    public void valueFromBytes_True() throws UnsupportedEncodingException {
        final Value value = ByteValue.valueFromBytes(createStream((byte) 0x03, (byte) 0x01));
        assertThat(value.getType(), is(Type.BOOLEAN));
        assertThat(value.getBooleanValue(), is(true));
        assertSame(value, Value.getTrue());
    }

    @Test
    public void valueFromBytes_False() throws UnsupportedEncodingException {
        final Value value = ByteValue.valueFromBytes(createStream((byte) 0x03, (byte) 0x00));
        assertThat(value.getType(), is(Type.BOOLEAN));
        assertThat(value.getBooleanValue(), is(false));
        assertSame(value, Value.getFalse());
    }

    @Test
    public void valueFromBytes_Integer() throws UnsupportedEncodingException {
        final Value value = ByteValue.valueFromBytes(createStream((byte) 0x01, (byte) 0x2a, (byte) 0x00, (byte) 0x00,
                                                                  (byte) 0x00));
        assertThat(value.getType(), is(Type.INTEGER));
        assertThat(value.getIntegerValue(), is(42));
    }

    @Test
    public void valueFromBytes_Float() throws UnsupportedEncodingException {
        final Value value = ByteValue.valueFromBytes(createStream((byte) 0x02, (byte) 0x56, (byte) 0x0e, (byte) 0x49,
                                                                  (byte) 0x40));
        assertThat(value.getType(), is(Type.FLOAT));
        assertThat(value.getFloatValue(), is(3.1415f));
    }

    @Test
    public void valueFromBytes_String() throws UnsupportedEncodingException {
        final Value value = ByteValue.valueFromBytes(createStream((byte) 0x04, (byte) 0x06, (byte) 0x66, (byte) 0x6f,
                                                                  (byte) 0x6f, (byte) 0x62, (byte) 0x61, (byte) 0x72));
        assertThat(value.getType(), is(Type.STRING));
        assertThat(value.getStringValue(), is("foobar"));
    }

    @Test
    public void bytesFromValue_Nil() throws UnsupportedEncodingException {
        final byte[] bytes = ByteValue.bytesFromValue(Value.getNil());
        assertThat(bytes, is(new byte[]{0x00, }));
    }

    @Test
    public void bytesFromValue_False() throws UnsupportedEncodingException {
        final byte[] bytes = ByteValue.bytesFromValue(Value.getFalse());
        assertThat(bytes, is(new byte[]{0x03, 0x00, }));
    }

    @Test
    public void bytesFromValue_True() throws UnsupportedEncodingException {
        final byte[] bytes = ByteValue.bytesFromValue(Value.getTrue());
        assertThat(bytes, is(new byte[]{0x03, 0x01, }));
    }

    @Test
    public void bytesFromValue_Integer() throws UnsupportedEncodingException {
        final byte[] bytes = ByteValue.bytesFromValue(Value.valueOf(42));
        assertThat(bytes, is(new byte[]{0x01, 0x2a, 0x00, 0x00, 0x00, }));
    }

    @Test
    public void bytesFromValue_Float() throws UnsupportedEncodingException {
        final byte[] bytes = ByteValue.bytesFromValue(Value.valueOf(3.1415f));
        assertThat(bytes, is(new byte[]{0x02, 0x56, 0x0e, 0x49, 0x40, }));
    }

    @Test
    public void bytesFromValue_String() throws UnsupportedEncodingException {
        final byte[] bytes = ByteValue.bytesFromValue(Value.valueOf("foobar"));
        assertThat(bytes, is(new byte[]{0x04, 0x06, 0x66, 0x6f, 0x6f, 0x62, 0x61, 0x72, }));
    }
}

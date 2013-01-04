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
import de.weltraumschaf.registermachine.typing.Value;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ByteValue {

    public static final byte NIL = 0x00;
    public static final byte INTEGER = 0x01;
    public static final byte FLOAT = 0x02;
    public static final byte BOOLEAN = 0x03;
    public static final byte STRING = 0x04;

    private ByteValue() {
        super();
    }

    public static Value valueFromBytes(final ByteCodeStream bytes) throws UnsupportedEncodingException {
        Value value;
        final byte type = bytes.getCurrentByte();
        bytes.nextByte(); // consume type byte
        switch (type) {
            case NIL:
                value = Value.getNil();
                break;
            case BOOLEAN:
                if (0 == bytes.getCurrentByte()) {
                    value = Value.getFalse();
                } else {
                    value = Value.getTrue();
                }
                bytes.nextByte(); // consume value byte
                break;
            case INTEGER:
                value = Value.valueOf(ByteInteger.intFromBytes(bytes.getBytes(4)));
                break;
            case FLOAT:
                value = Value.valueOf(ByteFloat.floatFromBytes(bytes.getBytes(4)));
                break;
            case STRING:
                final byte length = bytes.getCurrentByte();
                bytes.nextByte();
                value = Value.valueOf(ByteString.stringFromBytes(bytes.getBytes(length)));
                break;
            default:
                throw new IllegalArgumentException("Unrecognized type byte: " + type + "!");
        }

        return value;
    }

    public static byte[] bytesFromValue(final Value value) throws UnsupportedEncodingException {
        byte[] bytes;
        switch (value.getType()) {
            case NIL:
                bytes = new byte[]{NIL};
                break;
            case BOOLEAN:
                if (value.getBooleanValue()) {
                    bytes = new byte[]{BOOLEAN, 0x01};
                } else {
                    bytes = new byte[]{BOOLEAN, 0x00};
                }
                break;
            case INTEGER: {
                final byte[] intBytes = ByteInteger.bytesFromInt(value.getIntegerValue());
                bytes = new byte[]{
                    INTEGER,
                    intBytes[ByteInteger.FIRST_BYTE],
                    intBytes[ByteInteger.SECOND_BYTE],
                    intBytes[ByteInteger.THIRD_BYTE],
                    intBytes[ByteInteger.FOURTH_BYTE],
                };
                break;
            }
            case FLOAT: {
                final byte[] floatBytes = ByteFloat.bytesFromFloat(value.getFloatValue());
                bytes = new byte[]{
                    FLOAT,
                    floatBytes[ByteInteger.FIRST_BYTE],
                    floatBytes[ByteInteger.SECOND_BYTE],
                    floatBytes[ByteInteger.THIRD_BYTE],
                    floatBytes[ByteInteger.FOURTH_BYTE],
                };
                break;
            }
            case STRING: {
                final byte[] stringBytes = ByteString.bytesFromString(value.getStringValue());
                bytes = new byte[stringBytes.length + 2];
                bytes[0] = STRING;
                bytes[1] = (byte) stringBytes.length;
                for (int i = 0; i < stringBytes.length; ++i) {
                    bytes[(i + 2)] = stringBytes[i];
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Unrecognized type byte: " + value.getType() + "!");
        }
        return bytes;
    }
}

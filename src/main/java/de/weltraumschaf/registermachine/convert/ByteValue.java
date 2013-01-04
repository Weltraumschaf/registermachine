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

/**
 * Converts {@link Value} to byte code compatible byte arrays and vice versa.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ByteValue {

    /** Type byte for NIL. */
    public static final byte NIL = 0x00;
    /** Type byte for integers. */
    public static final byte INTEGER = 0x01;
    /** Type byte for floats. */
    public static final byte FLOAT = 0x02;
    /** Type byte for booleans. */
    public static final byte BOOLEAN = 0x03;
    /** Type byte for strings. */
    public static final byte STRING = 0x04;

    /**
     * Hide constructor for pure static class.
     */
    private ByteValue() {
        super();
    }

    /**
     * Extracts a {@link Value} from the current position of the {@link ByteCodeStream}.
     *
     * This method expects that the current byte of the stream is a valid type byte and will
     * extract the type from that position. After calling this method the passed in streams
     * internal index may have been advanced.
     *
     * @param bytes stream to extract from
     * @return value object
     * @throws UnsupportedEncodingException if, utf-8 errors occures with string
     */
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
                value = Value.valueOf(ByteInteger.intFromBytes(bytes.getBytes(ByteInteger.BYTES_PER_INT)));
                break;
            case FLOAT:
                value = Value.valueOf(ByteFloat.floatFromBytes(bytes.getBytes(ByteInteger.BYTES_PER_INT)));
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

    /**
     * Generates byte code value representation of given value.
     *
     * @param value to convert
     * @return array of bytes with different length
     * @throws UnsupportedEncodingException if, utf-8 errors occures with string
     */
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

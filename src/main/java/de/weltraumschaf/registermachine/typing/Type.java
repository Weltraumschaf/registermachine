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

package de.weltraumschaf.registermachine.typing;

/**
 * Types the VM knows.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum Type {

    /**
     * Nil.
     */
    NIL((byte) 0x00),
    /**
     * 32 bit integers.
     */
    INTEGER((byte) 0x01),
    /**
     * 32 bit float.
     */
    FLOAT((byte) 0x02),
    /**
     * Boolean.
     */
    BOOLEAN((byte) 0x03),
    /**
     * UTF-8 strings.
     */
    STRING((byte) 0x04);

    /**
     * byte representation of type.
     */
    private final byte typeByte;

    /**
     * Dedicated constructor.
     *
     * @param typeByte byte representing the type
     */
    Type(final byte typeByte) {
        this.typeByte = typeByte;
    }

    /**
     * Get the byte representation of the type.
     *
     * @return as byte
     */
    public byte asByte() {
        return typeByte;
    }

}

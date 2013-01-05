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

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;

/**
 * Helper to convert native byte arrays to boxed byte arrays and vice versa and also handles byte list.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ByteArray {

    /**
     * Hidden because pure static class.
     */
    private ByteArray() {
        super();
    }

    /**
     * Converts array of boxed bytes to unboxed native bytes.
     *
     * @param in boxed bytes
     * @return unboxed bytes
     * @deprecated Use ArrayUtils.toPrimitive().
     */
    @Deprecated public static byte[] toNative(final Byte[] in) {
        return ArrayUtils.toPrimitive(in);
    }

    /**
     * Converts array of unboxed bytes to boxed native bytes.
     *
     * @param in unboxed bytes
     * @return boxed bytes
     * @deprecated Use ArrayUtils.toObject().
     */
    @Deprecated public static Byte[] toObject(final byte[] in) {
        return ArrayUtils.toObject(in);
    }

    /**
     * Converts a list of boxed bytes to an array of native bytes.
     *
     * @param original list of boxed bytes
     * @return array of native bytes
     */
    public static byte[] convertToNativeArray(final List<Byte> original) {
        final List<Byte> in = Lists.newArrayList(original);
        return ByteArray.toNative(in.toArray(new Byte[in.size()]));
    }
}

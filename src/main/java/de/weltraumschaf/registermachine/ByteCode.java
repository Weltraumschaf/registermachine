/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a beer in return.
 *
 */

package de.weltraumschaf.registermachine;

enum ByteCode {
    ILOAD(1),
    ISTORE(2),
    IADD(3),
    ISUB(4),
    IMUL(5),
    IDIV(6),
    IPRINT(7),
    IF_GOTO(8),
    END(9);

    private final byte code;

    ByteCode(final int code) {
        this.code = (byte) code;
    }
}
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
package de.weltraumschaf.registermachine.bytecode;

import com.google.common.collect.Maps;
import de.weltraumschaf.registermachine.Const;
import java.util.Map;

/**
 * Opcodes of the byte code.
 *
 * General format:
 * <pre>
 * [ opcode 1 byte ]
 * [ opcode 1 byte ] [ arg1 4 byte ]
 * [ opcode 1 byte ] [ arg1 4 byte ] [ arg2 4 byte ]
 * [ opcode 1 byte ] [ arg1 4 byte ] [ arg2 4 byte ] [ arg3 4 byte ]
 * <pre>
 *
 * TODO Make result register implicitly to 0.
 *
 * @author sxs
 */
public enum OpCode {

    /**
     * Copy a value between registers.
     *
     * <pre>
     * move SRC_REG DST_REG ; copy register SRC_REG into register DST_REG
     * </pre>
     */
    MOVE(0x01, ArgCount.TWO),
    /**
     * Load a constant into a register.
     *
     * <pre>
     * loadc DST_REG VALUE ; load integer value into register DST_REG
     * </pre>
     */
    LOADC(0x02, ArgCount.TWO),
    /**
     * Addition operator.
     *
     * <pre>
     * add RES_REG OP1_REG OP2_REG ; add value from OP1_REG and OP2_REG and stores result in RES_REG
     * </pre>
     */
    ADD(0x03, ArgCount.TWO),
    /**
     * Subtraction operator.
     *
     * <pre>
     * sub RES_REG OP1_REG OP2_REG ; subtract value from OP1_REG and OP2_REG and stores result in RES_REG
     * </pre>
     */
    SUB(0x04, ArgCount.TWO),
    /**
     * Multiplication operator.
     *
     * <pre>
     * mul RES_REG OP1_REG OP2_REG ; multiply value from OP1_REG and OP2_REG and stores result in RES_REG
     * </pre>
     */
    MUL(0x05, ArgCount.TWO),
    /**
     * Division operator.
     *
     * <pre>
     * div RES_REG OP1_REG OP2_REG ; divide value from OP1_REG and OP2_REG and stores result in RES_REG
     * </pre>
     */
    DIV(0x06, ArgCount.TWO),
    /**
     * Modulus (remainder) operator.
     *
     * <pre>
     * mod RES_REG OP1_REG OP2_REG ; divide value from OP1_REG and OP2_REG and stores result in RES_REG
     * </pre>
     */
    MOD(0x07, ArgCount.TWO),
    /**
     * Exponentiation operator.
     */
    POW(0x08, ArgCount.TWO),
    /**
     * Unary minus operator.
     */
    UNM(0x09, ArgCount.ONE),
    /**
     * Logical NOT operator.
     */
    NOT(0x0a, ArgCount.ONE),
    /**
     * Unconditional jump.
     */
    JMP(0x0b, ArgCount.ONE),
    /**
     * Equality test.
     */
    EQ(0x0c, ArgCount.TWO),
    /**
     * Less than test Less than or equal to test.
     */
    LT(0x0d, ArgCount.TWO),
    /**
     * Less than or equals test Less than or equal to test.
     */
    LE(0x0e, ArgCount.TWO),
    /**
     *Boolean test, with conditional jump.
     */
    TEST(0x0f, ArgCount.TWO),
    /**
     *
     */
    PRINT(0x10, ArgCount.ONE),
    /**
     *
     */
    PRINTLN(0x11, ArgCount.ONE),
    /**
     * Unknown opcode wil stop execution.
     */
    UNKWONN(0xff);

    private static final Map<String, OpCode> MNEMONIC_LOOKUP = Maps.newHashMap();
    private static final Map<Byte, OpCode> OPCODE_LOOKUP = Maps.newHashMap();

    static {
        for (final OpCode code : OpCode.values()) {
            MNEMONIC_LOOKUP.put(code.name().toLowerCase(Const.LOCALE), code);
            OPCODE_LOOKUP.put(Byte.valueOf(code.getCode()), code);
        }
    }

    private final byte code;
    private final ArgCount argCount;

    OpCode(final int code) {
        this(code, ArgCount.NONE);
    }

    OpCode(final int code, ArgCount argCount) {
        this((byte) code, argCount);
    }

    OpCode(final byte code, ArgCount argCount) {
        this.code = code;
        this.argCount = argCount;
    }

    public byte getCode() {
        return code;
    }

    public ArgCount getArgCount() {
        return argCount;
    }

    @Override
    public String toString() {
        return String.format("%s[0x%s]", name(), toHex(code));
    }

    public String toHex() {
        return toHex(code);
    }

    public static String toHex(final byte b) {
        final String hex = Integer.toHexString(0xFF & b);
        return hex.length() == 1
                ? "0" + hex
                : hex;
    }

    public static OpCode lokup(final String mnemonic) {
        if (MNEMONIC_LOOKUP.containsKey(mnemonic.toLowerCase(Const.LOCALE))) {
            return MNEMONIC_LOOKUP.get(mnemonic.toLowerCase(Const.LOCALE));
        }

        return UNKWONN;
    }

    public static OpCode lookup(final byte b) {
        if (OPCODE_LOOKUP.containsKey(Byte.valueOf(b))) {
            return OPCODE_LOOKUP.get(Byte.valueOf(b));
        }

        return UNKWONN;
    }

    public enum ArgCount {
        NONE(0), ONE(1), TWO(2), THREE(3);

        private final int count;

        private ArgCount(final int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

    }

}
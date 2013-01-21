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
import de.weltraumschaf.registermachine.App;
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
 * </pre>
 *
 * @author sxs
 */
public enum OpCode {

    /**
     * No operation.
     */
    NOP(0x00),
    /**
     * Copy a value between registers.
     *
     * <pre>
     * move SRC_REG DST_REG ; copy register SRC_REG into register DST_REG
     * </pre>
     */
    MOVE(0x01, ArgCount.TWO),
    /**
     * Load variable from slot A into register B.
     */
    LOAD(0x02, ArgCount.TWO),
    /**
     * Store variable from register A into slot B.
     */
    STORE(0x03, ArgCount.TWO),
    /**
     * Load a constant into a register.
     *
     * <pre>
     * loadc DST_REG VALUE ; load integer value into register DST_REG
     * </pre>
     */
    LOADC(0x04, ArgCount.TWO),
    /**
     * Addition operator.
     *
     * <pre>
     * add OP1_REG OP2_REG ; add value from OP1_REG and OP2_REG and stores result in register 0
     * </pre>
     */
    ADD(0x05, ArgCount.THREE),
    /**
     * Subtraction operator.
     *
     * <pre>
     * sub OP1_REG OP2_REG ; subtract value from OP1_REG and OP2_REG and stores result in register 0
     * </pre>
     */
    SUB(0x06, ArgCount.THREE),
    /**
     * Multiplication operator.
     *
     * <pre>
     * mul RES_REG OP1_REG OP2_REG ; multiply value from OP1_REG and OP2_REG and stores result in RES_REG
     * </pre>
     */
    MUL(0x07, ArgCount.THREE),
    /**
     * Division operator.
     *
     * <pre>
     * div OP1_REG OP2_REG ; divide value from OP1_REG and OP2_REG and stores result in register 0
     * </pre>
     */
    DIV(0x08, ArgCount.THREE),
    /**
     * Modulus (remainder) operator.
     *
     * <pre>
     * mod OP1_REG OP2_REG ; reminder divide value from OP1_REG and OP2_REG and stores result in register 0
     * </pre>
     */
    MOD(0x09, ArgCount.THREE),
    /**
     * Exponentiation operator.
     *
     * <pre>
     * pow OP1_REG OP2_REG ; powers value from OP1_REG and OP2_REG and stores result in register 0
     * </pre>
     */
    POW(0x0a, ArgCount.THREE),
    /**
     * Unary minus operator.
     */
    UNM(0x0b, ArgCount.TWO),
    /**
     * Logical not operator.
     */
    NOT(0x0c, ArgCount.TWO),
    /**
     * Unconditional jump.
     */
    JMP(0x0d, ArgCount.ONE),
    /**
     * Equality test.
     */
    EQ(0x0e, ArgCount.THREE),
    /**
     * Less than test Less than or equal to test.
     */
    LT(0x0f, ArgCount.THREE),
    /**
     * Less than or equals test Less than or equal to test.
     */
    LE(0x10, ArgCount.THREE),
    /**
     *Boolean test, with conditional jump.
     */
    TEST(0x11, ArgCount.TWO),
    /**
     * Prints content of register.
     */
    PRINT(0x12, ArgCount.ONE),
    /**
     * Prints content of register and a new line.
     */
    PRINTLN(0x13, ArgCount.ONE),
    /**
     * Return from function.
     */
    RETURN(0x14, ArgCount.NONE),
    /**
     * Unknown opcode will stop execution.
     */
    UNKWONN(0xff);

    /** Lookup table opcodes by mnemonic. */
    private static final Map<String, OpCode> MNEMONIC_LOOKUP = Maps.newHashMap();
    /** Lookup table opcodes by opcode byte. */
    private static final Map<Byte, OpCode> OPCODE_LOOKUP = Maps.newHashMap();
    private static final int HEX_MASK = 0xFF;

    static {
        // Initialize lookups.
        for (final OpCode code : OpCode.values()) {
            MNEMONIC_LOOKUP.put(code.name().toLowerCase(App.LOCALE), code);
            OPCODE_LOOKUP.put(Byte.valueOf(code.getCode()), code);
        }
    }

    /** Opcode byte. */
    private final byte code;
    /** Describes how much arguments an opcode has. */
    private final ArgCount argCount;

    OpCode(final int code) {
        this(code, ArgCount.NONE);
    }

    OpCode(final int code, final ArgCount argCount) {
        this((byte) code, argCount);
    }

    OpCode(final byte code, final ArgCount argCount) {
        this.code = code;
        this.argCount = argCount;
    }

    /**
     * Get opcode byte.
     *
     * @return
     */
    public byte getCode() {
        return code;
    }

    /**
     * Get arg count description.
     *
     * @return descriptiive enum
     */
    public ArgCount getArgCount() {
        return argCount;
    }

    @Override
    public String toString() {
        return String.format("%s[0x%s]", name(), toHex(code));
    }

    /**
     * Get the hex representation.
     *
     * Not preceded with "0x".
     *
     * @return two character string
     */
    public String toHex() {
        return toHex(code);
    }

    /**
     * Calculates a two character long hex string of a given byte.
     *
     * Numbers below 0x0f will be preceded with a '0' character.
     *
     * @param b byte to convert
     * @return two digit hex string
     */
    public static String toHex(final byte b) {
        final String hex = Integer.toHexString(HEX_MASK & b);
        return hex.length() == 1
                ? "0" + hex
                : hex;
    }

    /**
     * Lookup for the given mnemonic string if an opcode exists.
     *
     * @param mnemonic mnemonic name
     * @return {@link #UNKWONN} for unknown mnemonics
     */
    public static OpCode lokup(final String mnemonic) {
        if (MNEMONIC_LOOKUP.containsKey(mnemonic.toLowerCase(App.LOCALE))) {
            return MNEMONIC_LOOKUP.get(mnemonic.toLowerCase(App.LOCALE));
        }

        return UNKWONN;
    }

    /**
     * Lookup for the given opcode byte if an opcode exists.
     *
     * @param b opcode byte
     * @return {@link #UNKWONN} for unknown mnemonics
     */
    public static OpCode lookup(final byte b) {
        if (OPCODE_LOOKUP.containsKey(Byte.valueOf(b))) {
            return OPCODE_LOOKUP.get(Byte.valueOf(b));
        }

        return UNKWONN;
    }

    /**
     * Describes the count of arguments an opcode has.
     */
    public enum ArgCount {
        NONE(0), ONE(1), TWO(2), THREE(3);

        /** Number of arguments. */
        private final int count;

        ArgCount(final int count) {
            this.count = count;
        }

        /**
         * Get count of required arguments.
         *
         * @return amount as integer >= 0
         */
        public int getCount() {
            return count;
        }

    }

}

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
     *
     * <pre>
     * nop ; does nothing
     * </pre>
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
     * Load variable into a register.
     *
     * <pre>
     * load DST_REG SLOT ; load variable from SLOT into register DST_REG
     * </pre>
     */
    LOAD(0x02, ArgCount.TWO),
    /**
     * Store variable from register A into slot B.
     *
     * <pre>
     * store DST_REG SLOT ; store value from register DST_REG into variable in SLOT
     * </pre>
     */
    STORE(0x03, ArgCount.TWO),
    /**
     * Load a constant into a register.
     *
     * <pre>
     * loadc DST_REG SLOT ; load constant from SLOT into register DST_REG
     * </pre>
     */
    LOADC(0x04, ArgCount.TWO),
    /**
     * Addition operator.
     *
     * <pre>
     * add RES_REG OP1_REG OP2_REG ; add value from OP1_REG and OP2_REG and stores result in register RES_REG
     * </pre>
     */
    ADD(0x05, ArgCount.THREE),
    /**
     * Subtraction operator.
     *
     * <pre>
     * sub RES_REG OP1_REG OP2_REG ; subtract value from OP1_REG and OP2_REG and stores result in register RES_REG
     * </pre>
     */
    SUB(0x06, ArgCount.THREE),
    /**
     * Multiplication operator.
     *
     * <pre>
     * mul RES_REG RES_REG OP1_REG OP2_REG ; multiply value from OP1_REG and OP2_REG and stores result in RES_REG
     * </pre>
     */
    MUL(0x07, ArgCount.THREE),
    /**
     * Division operator.
     *
     * <pre>
     * div RES_REG OP1_REG OP2_REG ; divide value from OP1_REG and OP2_REG and stores result in register RES_REG
     * </pre>
     */
    DIV(0x08, ArgCount.THREE),
    /**
     * Modulus (remainder) operator.
     *
     * <pre>
     * mod RES_REG OP1_REG OP2_REG ; reminder divide value from OP1_REG and OP2_REG and stores result
     *                             ; into register RES_REG
     * </pre>
     */
    MOD(0x09, ArgCount.THREE),
    /**
     * Exponentiation operator.
     *
     * <pre>
     * pow RES_REG OP1_REG OP2_REG ; powers value from OP1_REG and OP2_REG and stores result in register RES_REG
     * </pre>
     */
    POW(0x0a, ArgCount.THREE),
    /**
     * Unary minus operator.
     *
     * <pre>
     * unm RES_REG SRC_REG ; negates value in register SRC_REG and stores result into register RES_REG
     * </pre>
     */
    UNM(0x0b, ArgCount.TWO),
    /**
     * Logical not operator.
     *
     * <pre>
     * not RES_REG SRC_REG ; negates boolean value in register SRC_REG and stores result into register RES_REG
     * </pre>
     */
    NOT(0x0c, ArgCount.TWO),
    /**
     * Unconditional jump.
     *
     * <pre>
     * jmp DST_ADDR ; jumps to opcode at position DST_ADDR
     * </pre>
     */
    JMP(0x0d, ArgCount.ONE),
    /**
     * Equality test.
     *
     * <pre>
     * eq RES_REG OP1_REG OP2_REG ; compares values from register OP!_REG and OP2_REG and store result in RES_REG
     * </pre>
     */
    EQ(0x0e, ArgCount.THREE),
    /**
     * Less than test Less than or equal to test.
     *
     * <pre>
     * lt RES_REG OP1_REG OP2_REG ; compares values from register OP!_REG and OP2_REG and store result in RES_REG
     * </pre>
     */
    LT(0x0f, ArgCount.THREE),
    /**
     * Less than or equals test Less than or equal to test.
     *
     * <pre>
     * le RES_REG OP1_REG OP2_REG ; compares values from register OP!_REG and OP2_REG and store result in RES_REG
     * </pre>
     */
    LE(0x10, ArgCount.THREE),
    /**
     * Boolean test, with conditional jump.
     *
     * <pre>
     * test TEST_REG DST_ADDR ; if value in TEST_REG is true jumps to opcode at position DST_ADDR
     * </pre>
     */
    TEST(0x11, ArgCount.TWO),
    /**
     * Prints content of register.
     *
     * <pre>
     * print SRC_REG ; prints value from register SRC_REG
     * </pre>
     */
    PRINT(0x12, ArgCount.ONE),
    /**
     * Prints content of register and a new line.
     *
     * <pre>
     * println SRC_REG ; prints value from register SRC_REG
     * </pre>
     */
    PRINTLN(0x13, ArgCount.ONE),
    /**
     * Return from function.
     *
     * <pre>
     * return ; return from function
     * </pre>
     */
    RETURN(0x14, ArgCount.NONE),
    /**
     * Unknown opcode will stop execution.
     *
     * This opcode should never occur in the byte code.
     */
    UNKWONN(0xff);
    /**
     * Lookup table opcodes by mnemonic.
     */
    private static final Map<String, OpCode> MNEMONIC_LOOKUP = Maps.newHashMap();
    /**
     * Lookup table opcodes by opcode byte.
     */
    private static final Map<Byte, OpCode> OPCODE_LOOKUP = Maps.newHashMap();
    /**
     * Used as mask for hex string representation.
     */
    private static final int HEX_MASK = 0xff;

    static {
        // Initialize lookups.
        for (final OpCode code : OpCode.values()) {
            MNEMONIC_LOOKUP.put(code.name().toLowerCase(App.LOCALE), code);
            OPCODE_LOOKUP.put(Byte.valueOf(code.getCode()), code);
        }
    }
    /**
     * Opcode byte.
     */
    private final byte code;
    /**
     * Describes how much arguments an opcode has.
     */
    private final ArgCount argCount;

    /**
     * Initialize opcode with zero arguments.
     *
     * @param code opcode number
     */
    OpCode(final int code) {
        this(code, ArgCount.NONE);
    }

    /**
     * Initialize opcode with code and arguments.
     *
     * @param code opcode number
     * @param argCount number of arguments
     */
    OpCode(final int code, final ArgCount argCount) {
        this((byte) code, argCount);
    }

    /**
     * Dedicated constructor.
     *
     * @param code opcode number
     * @param argCount number of arguments
     */
    OpCode(final byte code, final ArgCount argCount) {
        this.code = code;
        this.argCount = argCount;
    }

    /**
     * Get opcode byte.
     *
     * @return opcode number
     */
    public byte getCode() {
        return code;
    }

    /**
     * Get arg count description.
     *
     * @return description enum
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

        /**
         * No arguments.
         */
        NONE(0),
        /**
         * One argument.
         */
        ONE(1),
        /**
         * Two arguments.
         */
        TWO(2),
        /**
         * Three arguments.
         */
        THREE(3);
        /**
         * Number of arguments.
         */
        private final int count;

        /**
         * Dedicated constructor.
         *
         * @param count number of arguments.
         */
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

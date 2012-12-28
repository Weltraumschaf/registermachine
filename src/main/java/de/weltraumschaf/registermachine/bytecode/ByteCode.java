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
import java.util.Locale;
import java.util.Map;

/**
 * Opcodes of the byte code.
 *
 * General format:
 * <pre>
 * [ opcode 1 byte ]
 * [ opcode 1 byte ] [ arg1 4 byte ]
 * [ opcode 1 byte ] [ arg1 4 byte ] ... [ argN 4 byte ]
 * <pre>
 * @author sxs
 */
public enum ByteCode {

    /**
     * Copy a value between registers.
     *
     * <pre>
     * move SRC_REG DST_REG ; copy register SRC_REG to register DST_REG
     * 0x00 0x01 0x02
     * </pre>
     *
     *
     */
    MOVE(0, ArgCount.TWO),
    /**
     * Load a constant into a register.
     *
     * 0x01
     */
//    LOADC(1),
    /**
     * Load a boolean into a register.
     *
     * <pre>
     * LOADBOOL DST_REG ; load bool
     * 0x02 0x01
     * </pre>
     */
//    LOADBOOL(2),
    /**
     * Load nil values into a range of registers.
     *
     * 0x03
     */
//    LOADNIL(3),
    /**
     * Read an upvalue into a register.
     *
     * 0x04
     */
//    GETUPVAL(4),
    /**
     * Read a global variable into a register.
     *
     * <pre>
     * getglobal
     * 0x05
     * </pre>
     */
//    GETGLOBAL(5),
    /**
     * Read a table element into a register.
     *
     * 0x06
     */
//    GETTABLE(6),
    /**
     * Write a register value into a global variable.
     *
     * 0x07
     */
//    SETGLOBAL(7),
    /**
     * Write a register value into an upvalue.
     *
     * 0x08
     */
//    SETUPVAL(8),
    /**
     * Write a register value into a table element.
     *
     * 0x09
     */
//    SETTABLE(9),
    /**
     * Create a new table.
     *
     * 0x0a
     */
//    NEWTABLE(10),
    /**
     * Prepare an object method for calling.
     *
     * 0x0b
     */
//    SELF(11),
    /**
     * Addition operator.
     *
     * <pre>
     * add RES_REG OP1_REG OP2_REG ; add value from OP1_REG and OP2_REG and stores result in RES_REG
     * 0x0c 0x01 0x02 0x03
     * </pre>
     */
    ADD(12, ArgCount.THREE),
    /**
     * Subtraction operator.
     *
     * <pre>
     * sub RES_REG OP1_REG OP2_REG ; subtract value from OP1_REG and OP2_REG and stores result in RES_REG
     * 0x0d 0x01 0x02 0x03
     * </pre>
     */
    SUB(13, ArgCount.THREE),
    /**
     * Multiplication operator.
     *
     * <pre>
     * mul RES_REG OP1_REG OP2_REG ; multiply value from OP1_REG and OP2_REG and stores result in RES_REG
     * 0x0e 0x01 0x02 0x03
     * </pre>
     */
    MUL(14, ArgCount.THREE),
    /**
     * Division operator.
     *
     * <pre>
     * div RES_REG OP1_REG OP2_REG ; divide value from OP1_REG and OP2_REG and stores result in RES_REG
     * 0x0f 0x01 0x02 0x03
     * </pre>
     */
    DIV(15, ArgCount.THREE),
    /**
     * Modulus (remainder) operator.
     *
     * 0x10
     */
    MOD(16),
    /**
     * Exponentiation operator.
     *
     * 0x11
     */
    POW(17),
    /**
     * Unary minus operator.
     *
     * 0x12
     */
    UNM(18),
    /**
     * Logical NOT operator.
     *
     * 0x13
     */
    NOT(19),
    /**
     * Length operator.
     *
     * 0x14
     */
//    LEN(20),
    /**
     * Concatenate a range of registers.
     *
     * 0x15
     */
//    CONCAT(21),
    /**
     * Unconditional jump.
     *
     * 0x16
     */
    JMP(22),
    /**
     * Equality test.
     *
     * 0x17
     */
    EQ(23),
    /**
     * Less than test Less than or equal to test.
     *
     * 0x18
     */
    LT(24),
    /**
     * Boolean test, with conditional jump.
     *
     * 0x19
     */
    LE(25),
    /**
     * Boolean test, with conditional jump and assignment.
     *
     * 0x1a
     */
    TEST(26),
    /**
     * Boolean test, with conditional jump and assignment.
     *
     * 0x1b
     */
    TESTSET(27),
    /**
     * Call a closure.
     *
     * 0x1c
     */
//    CALL(28),
    /**
     * Perform a tail call.
     *
     * 0x1d
     */
//    TAILCALL(29),
    /**
     *
     * Return from function call.
     *
     * 0x1e
     */
    RETURN(30),
    /**
     * Iterate a numeric for loop.
     *
     * 0x1f
     */
    FORLOOP(31),
    /**
     * Initialization for a numeric for loop.
     *
     * 0x20
     */
    FORPREP(32),
    /**
     * Iterate a generic for loop.
     *
     * 0x21
     */
//    TFORLOOP(33),
    /**
     * Set a range of array elements for a table.
     *
     * 0x22
     */
//    SETLIST(34),
    /**
     * Close a range of locals being used as upvalues.
     *
     * 0x23
     */
//    CLOSE(35),
    /**
     * Create a closure of a function prototype.
     *
     * 0x24
     */
//    CLOSURE(36),
    /**
     * Assign vararg function arguments to registers.
     *
     * 0x25
     */
//    VARARG(37),
    UNKWONN(-1);

    private static final Map<String, ByteCode> MNEMONIC_LOOKUP = Maps.newHashMap();
    private static final Map<Byte, ByteCode> OPCODE_LOOKUP = Maps.newHashMap();

    static {
        for (final ByteCode code : ByteCode.values()) {
            MNEMONIC_LOOKUP.put(code.name().toLowerCase(Locale.ENGLISH), code);
            OPCODE_LOOKUP.put(Byte.valueOf(code.getCode()), code);
        }
    }

    private final byte code;
    private final ArgCount argCount;

    ByteCode(final int code) {
        this(code, ArgCount.NONE);
    }

    ByteCode(final int code, ArgCount argCount) {
        this((byte) code, argCount);
    }

    ByteCode(final byte code, ArgCount argCount) {
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

    public static ByteCode lokup(final String mnemonic) {
        if (MNEMONIC_LOOKUP.containsKey(mnemonic.toLowerCase(Locale.ENGLISH))) {
            return MNEMONIC_LOOKUP.get(mnemonic.toLowerCase(Locale.ENGLISH));
        }

        return UNKWONN;
    }

    public static ByteCode lookup(final byte b) {
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
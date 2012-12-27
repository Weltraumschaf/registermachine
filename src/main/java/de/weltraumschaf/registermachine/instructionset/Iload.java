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

package de.weltraumschaf.registermachine.instructionset;

import de.weltraumschaf.registermachine.Configuration;

public class Iload implements Instruction {

    private final int register;
    private int value = 0;
    private int address = -1;

    public  Iload(final int register, final int value) {
        this(register);
        this.value = value;
    }

    public  Iload(final int register, final String value) {
        this(register);

        if (value.charAt(0) == '#') {
            this.address = Integer.valueOf(value.substring(1));
        } else {
            throw new IllegalArgumentException(String.format("Invalid address format passed '%s'! Must be of form '#1234'.", value));
        }
    }

    public  Iload(final int register) {
        super();
        this.register = register;
    }

    public boolean loadsReferencedValue() {
        return -1 == address;
    }

    @Override
    public void evaluate(final Configuration config) {
        Object val;

        if (loadsReferencedValue()) {
            val = config.getScope().getAssign(address);
        } else {
            val = value;
        }

        throw new UnsupportedOperationException();
//        config.setRegister(register, val);
//        config.incInstructionCounter();
    }

    @Override
    public String toString() {
        String loaded;

        if (loadsReferencedValue()) {
            loaded = "#" + address;
        } else {
            loaded = String.valueOf(value);
        }

        return String.format("iload %s, %s", register, loaded);
    }

}
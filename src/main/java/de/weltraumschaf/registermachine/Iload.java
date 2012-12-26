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

class Iload implements Instruction {

    private final int register;
    private final int value;
    private final int valueAdress;

    public  Iload(final int register, final Object value) {
        this.register = register;

        if (value instanceof Integer) {
            this.value = (Integer) value;
            this.valueAdress = -1;
        } else if (value instanceof String) {
            this.value = -1;

            if (((String)value).charAt(0) == '#') {
                this.valueAdress = Integer.valueOf(((String)value).substring(1));
            } else {
                throw new IllegalArgumentException(String.format("Invalid address format passed '%s'!", value));
            }
        } else {
            throw new IllegalArgumentException(String.format("Unsupported value passed '%s'! Allowed are integer constants or addresses.", value));
        }
    }

    @Override
    public void evaluate(final Configuration config) {
        Object val;

        if (-1 != valueAdress) {
            val = config.getScope().getAssign(valueAdress);
        } else {
            val = value;
        }

        throw new UnsupportedOperationException();
//        config.setRegister(register, val);
//        config.incInstructionCounter();
    }

    @Override
    public String toString() {
        String v;

        if (-1 != valueAdress) {
            v = "#" + valueAdress;
        } else {
            v = "" + value;
        }

        return String.format("iload %s, %s", register, value);
    }

}
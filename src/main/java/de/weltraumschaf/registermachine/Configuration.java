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

public class Configuration {
    private static final int DEFAULT_SIZE = 16;

    private int ic = 0; // instruction counter
    private int[] registers;
    private final int size;

    private final Scope scope= new Scope();

    public  Configuration() {
        this(DEFAULT_SIZE);
    }

    public  Configuration(final int size) {
        registers = new int[size];
        this.size = size;
        init();
    }

    public void init() {
        for (int i = 0; i < size; i++) {
            registers[i] = 0;
        }
    }

    public int getInstructionCounter() {
        return ic;
    }

    public void setInstructionCounter(final int ic) {
        this.ic = ic;
    }

    public void incInstructionCounter() {
        ic++;
    }

    public void setRegister(final int i, final int val) {
        registers[i] = val;
    }

    public int getRegister(final int i) {
        if (i >= size || i < 0) {
            throw new IndexOutOfBoundsException("Access of not existing register: i!");
        }

        return registers[i];
    }

    public Scope getScope() {
        return scope;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("icounter = ").append(ic + 1);

        for (int i = 0; i < size; i++) {
            sb.append(", \tr[").append(i).append("] = ").append(registers[i]);
        }

        if (!scope.isEmpty()) {
            sb.append(String.format("%n%s", scope.toString()));
        }

        return sb.toString();
    }

}
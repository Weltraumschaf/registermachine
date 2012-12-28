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
package de.weltraumschaf.registermachine.vm;

public class RuntimeConfiguration {

    private static final int DEFAULT_REGISTERS_COUNT = 16;
    private static final int DEFAULT_REGISTER_SIZE = 256;
    private final Scope scope = new Scope();
    private int ic = 0; // instruction counter
    private final Register registers;
    private int size;

    public RuntimeConfiguration() {
        this(DEFAULT_REGISTERS_COUNT);
    }

    public RuntimeConfiguration(final int size) {
        registers = new Register();
        this.size = size;
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

    public void setRegister(final int r, final int v) {
        registers.set(r, v);
    }

    public int getRegister(final int r) {
        return registers.get(r);
    }

    public Scope getScope() {
        return scope;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("icounter = ").append(ic + 1);

        for (int i = 0; i < size; i++) {
            sb.append(", \tr[")
                    .append(i)
                    .append("] = ")
                    .append(registers);
        }

        if (!scope.isEmpty()) {
            sb.append(String.format("%n%s", scope.toString()));
        }

        return sb.toString();
    }
}
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

import de.weltraumschaf.registermachine.Const;

public class RuntimeConfiguration {

    private final Scope scope = new Scope();
    private final Register registers = new Register();
    private int instructionCounter;

    public int getInstructionCounter() {
        return instructionCounter;
    }

    public void setInstructionCounter(final int ic) {
        this.instructionCounter = ic;
    }

    public void incInstructionCounter() {
        instructionCounter++;
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
        sb.append("Counter=")
          .append(instructionCounter)
          .append(" Registers=")
          .append(registers);

        if (!scope.isEmpty()) {
            sb.append(Const.NL).append(scope.toString());
        }

        return sb.toString();
    }
}
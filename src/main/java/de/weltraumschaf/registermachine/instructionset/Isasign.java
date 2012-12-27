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

import de.weltraumschaf.registermachine.RuntimeConfiguration;
import de.weltraumschaf.registermachine.Scope;

public class Isasign implements Instruction {

    private final int address;
    private final int value;

    public  Isasign(final int address, final int value) {
        super();
        this.address = address;
        this.value   = value;
    }

    @Override
    public void evaluate(final RuntimeConfiguration config) {
        throw new UnsupportedOperationException();
//        final Scope scope = config.getScope();
//        scope.setAssign(address, value);
//        config.incInstructionCounter();
    }

    @Override
    public String toString() {
        return String.format("isasign %d, %d", address, value);
    }

}
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

public class StdOut implements Instruction {
    private final int register;

    public  StdOut(final int i) {
        super();
        register = i;
    }

    @Override
    public void evaluate(final RuntimeConfiguration config) {
        System.out.print(config.getRegister(register));
        config.incInstructionCounter();
    }

    @Override
    public String toString() {
        return "stdout " + register;
    }
}
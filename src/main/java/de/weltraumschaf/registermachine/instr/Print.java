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

package de.weltraumschaf.registermachine.instr;

import de.weltraumschaf.commons.IO;
import de.weltraumschaf.registermachine.vm.RuntimeConfiguration;

class Print implements Instruction {
    private final int srcReg;
    private final IO io;

    public  Print(final int srcReg, final IO io) {
        super();
        this.srcReg = srcReg;
        this.io = io;
    }

    @Override
    public void evaluate(final RuntimeConfiguration config) {
        io.print(String.valueOf(config.getRegister(srcReg)));
        config.incInstructionCounter();
    }

    @Override
    public String toString() {
        return "print " + srcReg;
    }
}
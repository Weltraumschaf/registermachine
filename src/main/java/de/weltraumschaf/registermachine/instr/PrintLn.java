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

/**
 * Print line instruction.
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
class PrintLn implements Instruction {

    /**
     * Source register.
     */
    private final int srcReg;
    private final IO io;

    public  PrintLn(final int srcReg, final IO io) {
        super();
        this.srcReg = srcReg;
        this.io = io;
    }

    @Override
    public void evaluate(final RuntimeConfiguration config) {
        io.println(config.getRegister(srcReg).toString());
    }

    @Override
    public String toString() {
        return "println " + srcReg;
    }
}

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

public class Isub implements Instruction {

    private final int resultReg;
    private final int op1Reg;
    private final int op2Reg;

    public Isub(final int resultReg, final int op1Reg, final int op2Reg) {
        super();
        this.resultReg = resultReg;
        this.op1Reg    = op1Reg;
        this.op2Reg    = op2Reg;
    }

    @Override
    public void evaluate(RuntimeConfiguration config) {
        throw new UnsupportedOperationException();
//        final int op1 = config.getRegister(op1Reg);
//        final int op2 = config.getRegister(op2Reg);
//        config.setRegister(resultReg, op1 - op2);
//        config.incInstructionCounter();
    }

    @Override
    public String toString() {
        return String.format("isub %d, %d, %d", op1Reg, op2Reg, resultReg);
    }

}
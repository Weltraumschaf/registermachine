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

import de.weltraumschaf.registermachine.vm.RuntimeConfiguration;

public class Iadd implements Instruction {

    private final int resultReg;
    private final int op1Reg;
    private final int op2Reg;

    public Iadd(final int resultReg, final int op1Reg, final int op2Reg) {
        this.resultReg = resultReg;
        this.op1Reg = op1Reg;
        this.op2Reg = op2Reg;
    }

    @Override
    public void evaluate(final RuntimeConfiguration config) {
        throw new UnsupportedOperationException();
//        final int op1 = config.getRegister(this.op1Reg);
//        final int op2 = config.getRegister(this.op2Reg);
//        config.setRegister(this.resultReg, op1 + op2);
//        config.incInstructionCounter();
    }

    @Override
    public String toString() {
        return String.format("iadd %d, %d, %d", op1Reg, op2Reg, resultReg);
    }
}
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

import de.weltraumschaf.registermachine.typing.Value;
import de.weltraumschaf.registermachine.vm.RuntimeConfiguration;

class Div implements Instruction {

    private final int op1Reg;
    private final int op2Reg;

    public  Div(final int op1Reg, final int op2Reg) {
        this.op1Reg    = op1Reg;
        this.op2Reg    = op2Reg;
    }

    @Override
    public void evaluate(final RuntimeConfiguration config) {
        final Value op1 = config.getRegister(op1Reg);
        final Value op2 = config.getRegister(op2Reg);
        config.setRegister(RESULT_REGISTER, new Value(op1.getIntegerValue() / op2.getIntegerValue()));
    }

    @Override
    public String toString() {
        return String.format("div %d %d", op1Reg, op2Reg);
    }

}
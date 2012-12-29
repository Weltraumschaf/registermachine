/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */

package de.weltraumschaf.registermachine.instr;

import de.weltraumschaf.registermachine.typing.Value;
import de.weltraumschaf.registermachine.vm.RuntimeConfiguration;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Le implements Instruction {

    private final int op1Reg;
    private final int op2Reg;

    public Le(int op1Reg, int op2Reg) {
        this.op1Reg = op1Reg;
        this.op2Reg = op2Reg;
    }

    @Override
    public void evaluate(RuntimeConfiguration config) {
        final Value op1 = config.getRegister(op1Reg);
        final Value op2 = config.getRegister(op2Reg);

        if (op1.getIntegerValue() <= op2.getIntegerValue()) {
            config.setRegister(RESULT_REGISTER, Value.getTrue());
        } else {
            config.setRegister(RESULT_REGISTER, Value.getFalse());
        }
    }

    @Override
    public String toString() {
        return String.format("le %d %d", op1Reg, op2Reg);
    }
}

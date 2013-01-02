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
public class Not implements Instruction {

    /**
     * Source register.
     */
    private final int srcReg;

    public Not(int srcReg) {
        super();
        this.srcReg = srcReg;
    }

    @Override
    public void evaluate(final RuntimeConfiguration config) {
        Value value = Value.getTrue();

        if (config.getRegister(srcReg).getBooleanValue()) {
            value = Value.getFalse();
        }

        config.setRegister(REG_A, value);
    }

    @Override
    public String toString() {
        return String.format("not %d", srcReg);
    }

}

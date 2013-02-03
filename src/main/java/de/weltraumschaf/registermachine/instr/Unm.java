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

import de.weltraumschaf.registermachine.inter.Value;
import de.weltraumschaf.registermachine.vm.RuntimeConfiguration;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class Unm implements Instruction {

    /**
     * Source register.
     */
    private final int srcReg;

    public Unm() {
        this(REG_B);
    }

    public Unm(int srcReg) {
        super();
        this.srcReg = srcReg;
    }

    @Override
    public void evaluate(final RuntimeConfiguration config) {
        config.setRegisterA(Value.valueOf(-config.getRegister(srcReg).getIntegerValue()));
    }

    @Override
    public String toString() {
        return String.format("unm %d", srcReg);
    }

}

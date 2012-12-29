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

import de.weltraumschaf.registermachine.vm.RuntimeConfiguration;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Test implements Instruction {

    private final int testReg;
    private final int jumpReg;

    public Test(int testReg, int jumpReg) {
        this.testReg = testReg;
        this.jumpReg = jumpReg;
    }

    @Override
    public void evaluate(RuntimeConfiguration config) {
        if (config.getRegister(testReg).getBooleanValue()) {
            config.setInstructionCounter(config.getRegister(jumpReg).getIntegerValue()); // Use arguemnt directly
        }
    }

    @Override
    public String toString() {
        return String.format("test %d %d", testReg, jumpReg);
    }

}

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
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class EqTest {

    private final Eq sut = new Eq(1, 2);

    @Test
    public void evaluate_firstOperandLessIsFalse() {
        RuntimeConfiguration config = new RuntimeConfiguration();
        config.setRegister(1, 2);
        config.setRegister(2, 5);
        sut.evaluate(config);
        assertThat(config.getRegister(Instruction.RESULT_REGISTER), is(0));
    }

    @Test
    public void evaluate_firstOperandGreaterIsFalse() {
        RuntimeConfiguration config = new RuntimeConfiguration();
        config.setRegister(1, 5);
        config.setRegister(2, 2);
        sut.evaluate(config);
        assertThat(config.getRegister(Instruction.RESULT_REGISTER), is(0));
    }

    @Test
    public void evaluate_sameIsTrue() {
        RuntimeConfiguration config = new RuntimeConfiguration();
        config.setRegister(1, 5);
        config.setRegister(2, 5);
        sut.evaluate(config);
        assertThat(config.getRegister(Instruction.RESULT_REGISTER), is(1));
    }

}
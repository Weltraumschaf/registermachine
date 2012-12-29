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
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class LeTest {

    private final Le sut = new Le(1, 2);

    @Test
    public void evaluate_firstOperandLessIsTrue() {
        RuntimeConfiguration config = new RuntimeConfiguration();
        config.setRegister(1, new Value(2));
        config.setRegister(2, new Value(5));
        sut.evaluate(config);
        assertThat(config.getRegister(Instruction.RESULT_REGISTER).getIntegerValue(), is(1));
    }

    @Test
    public void evaluate_firstOperandNotLessIsFalse() {
        RuntimeConfiguration config = new RuntimeConfiguration();
        config.setRegister(1, new Value(5));
        config.setRegister(2, new Value(2));
        sut.evaluate(config);
        assertThat(config.getRegister(Instruction.RESULT_REGISTER).getIntegerValue(), is(0));
    }

    @Test
    public void evaluate_sameIsTrue() {
        RuntimeConfiguration config = new RuntimeConfiguration();
        config.setRegister(1, new Value(5));
        config.setRegister(2, new Value(5));
        sut.evaluate(config);
        assertThat(config.getRegister(Instruction.RESULT_REGISTER).getIntegerValue(), is(1));
    }

}
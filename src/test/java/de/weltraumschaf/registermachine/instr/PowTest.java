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
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class PowTest {

    private final Instruction sut = new Pow();
    private final RuntimeConfiguration config = new RuntimeConfiguration();

    @Test
    public void powersTwoIntegers() {
        config.setRegisterB(Value.valueOf(4));
        config.setRegisterC(Value.valueOf(2));
        sut.evaluate(config);
        assertThat(config.getRegisterA(), is(Value.valueOf(16)));
    }

}

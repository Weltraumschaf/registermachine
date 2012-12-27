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
package de.weltraumschaf.registermachine.asm;

import de.weltraumschaf.registermachine.bytecode.ByteCodeFile;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class AssemblerTest {

    private static final String PACKAGE_PREFIX = "/de/weltraumschaf/registermachine/asm";
    private final Assembler sut = new Assembler();

    @Test
    public void assamble() throws IOException, AssemblerSyntaxException {
        final InputStream input = getClass().getResourceAsStream(PACKAGE_PREFIX + "/all_instructions.caythe");
        final ByteCodeFile bytecode = sut.assamble(input);
        input.close();
        assertThat(bytecode.isValid(), is(true));
        assertThat(bytecode.getVersion(), is((byte) 0x01));
        final byte[] program = bytecode.getProgramm();
        assertThat(program.length, is(19));
        assertThat(program[0], is((byte) 0x00)); // move
        assertThat(program[1], is((byte) 0x01)); // arg 1
        assertThat(program[2], is((byte) 0x02)); // arg 2
        assertThat(program[3], is((byte) 0x0c)); // add
        assertThat(program[4], is((byte) 0x01)); // arg 1
        assertThat(program[5], is((byte) 0x02)); // arg 2
        assertThat(program[6], is((byte) 0x03)); // arg 3
        assertThat(program[7], is((byte) 0x0d)); // sub
        assertThat(program[8], is((byte) 0x01)); // arg 1
        assertThat(program[9], is((byte) 0x02)); // arg 2
        assertThat(program[10], is((byte) 0x03)); // arg 3
        assertThat(program[11], is((byte) 0x0e)); // mul
        assertThat(program[12], is((byte) 0x01)); // arg 1
        assertThat(program[13], is((byte) 0x02)); // arg 2
        assertThat(program[14], is((byte) 0x03)); // arg 3
        assertThat(program[15], is((byte) 0x0f)); // div
        assertThat(program[16], is((byte) 0x01)); // arg 1
        assertThat(program[17], is((byte) 0x02)); // arg 2
        assertThat(program[18], is((byte) 0x03)); // arg 3
    }
}
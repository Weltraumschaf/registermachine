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
        final InputStream input = getClass().getResourceAsStream(PACKAGE_PREFIX + "/all_instructions.ctasm");
        final ByteCodeFile bytecode = sut.assamble(input);
        input.close();
        assertThat(bytecode.isValid(), is(true));
        assertThat(bytecode.getVersion(), is((short) 0x01));
        final byte[] program = bytecode.getProgramm();
        assertThat(program.length, is(45));
        assertThat(program[0], is((byte) 0x01)); // move

        assertThat(program[1], is((byte) 0x01)); // arg 1
        assertThat(program[2], is((byte) 0x00)); // arg 1
        assertThat(program[3], is((byte) 0x00)); // arg 1
        assertThat(program[4], is((byte) 0x00)); // arg 1

        assertThat(program[5], is((byte) 0x02)); // arg 2
        assertThat(program[6], is((byte) 0x00)); // arg 2
        assertThat(program[7], is((byte) 0x00)); // arg 2
        assertThat(program[8], is((byte) 0x00)); // arg 2

        assertThat(program[9], is((byte) 0x03)); // add

        assertThat(program[10], is((byte) 0x02)); // arg 1
        assertThat(program[11], is((byte) 0x00)); // arg 1
        assertThat(program[12], is((byte) 0x00)); // arg 1
        assertThat(program[13], is((byte) 0x00)); // arg 1

        assertThat(program[14], is((byte) 0x03)); // arg 2
        assertThat(program[15], is((byte) 0x00)); // arg 2
        assertThat(program[16], is((byte) 0x00)); // arg 2
        assertThat(program[17], is((byte) 0x00)); // arg 2

        assertThat(program[18], is((byte) 0x04)); // sub

        assertThat(program[19], is((byte) 0x02)); // arg 1
        assertThat(program[20], is((byte) 0x00)); // arg 1
        assertThat(program[21], is((byte) 0x00)); // arg 1
        assertThat(program[22], is((byte) 0x00)); // arg 1

        assertThat(program[23], is((byte) 0x03)); // arg 2
        assertThat(program[24], is((byte) 0x00)); // arg 2
        assertThat(program[25], is((byte) 0x00)); // arg 2
        assertThat(program[26], is((byte) 0x00)); // arg 2

        assertThat(program[27], is((byte) 0x05)); // mul

        assertThat(program[28], is((byte) 0x02)); // arg 1
        assertThat(program[29], is((byte) 0x00)); // arg 1
        assertThat(program[30], is((byte) 0x00)); // arg 1
        assertThat(program[31], is((byte) 0x00)); // arg 1

        assertThat(program[32], is((byte) 0x03)); // arg 2
        assertThat(program[33], is((byte) 0x00)); // arg 2
        assertThat(program[34], is((byte) 0x00)); // arg 2
        assertThat(program[35], is((byte) 0x00)); // arg 2

        assertThat(program[36], is((byte) 0x06)); // div

        assertThat(program[37], is((byte) 0x02)); // arg 1
        assertThat(program[38], is((byte) 0x00)); // arg 1
        assertThat(program[39], is((byte) 0x00)); // arg 1
        assertThat(program[40], is((byte) 0x00)); // arg 1

        assertThat(program[41], is((byte) 0x03)); // arg 2
        assertThat(program[42], is((byte) 0x00)); // arg 2
        assertThat(program[43], is((byte) 0x00)); // arg 2
        assertThat(program[44], is((byte) 0x00)); // arg 2
    }

}

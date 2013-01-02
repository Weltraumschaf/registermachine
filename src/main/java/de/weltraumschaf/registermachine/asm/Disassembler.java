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

import de.weltraumschaf.registermachine.App;
import de.weltraumschaf.registermachine.ByteInt;
import de.weltraumschaf.registermachine.bytecode.ByteCodeFile;
import de.weltraumschaf.registermachine.bytecode.ByteCodeStream;
import de.weltraumschaf.registermachine.bytecode.OpCode;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Disassembler {

    public String disassamble(final InputStream input) throws IOException {
        final StringBuilder buffer = new StringBuilder();
        final ByteCodeFile bc = new ByteCodeFile(input);

        if (!bc.isValid()) {
            throw new RuntimeException("Not a valid byte code file!");
        }

        buffer.append("; byte code version ").append(bc.getVersion()).append(App.NL);
        disassembleProgram(buffer, bc.getProgramm());
        return buffer.toString();
    }

    private void disassembleProgram(final StringBuilder buffer, final byte[] programm) {
        int i = 0;
        while (i < programm.length) {
            final OpCode bc = OpCode.lookup(programm[i]);

            if (OpCode.UNKWONN == bc) {
                throw new RuntimeException(String.format("Unknown opcode: %s!", bc.toHex()));
            }

            buffer.append(bc.name().toLowerCase(App.LOCALE));
            ++i;

            if (bc.getArgCount() != OpCode.ArgCount.NONE) {
                final int count = bc.getArgCount().getCount() * ByteCodeStream.ARG_BYTE_COUNT;
                final byte[] bytes = new byte[ByteCodeStream.ARG_BYTE_COUNT];
                for (int shift = 0; shift < count; ++shift) {
                    bytes[shift % ByteCodeStream.ARG_BYTE_COUNT] = programm[i];

                    if (shift % ByteCodeStream.ARG_BYTE_COUNT == ByteCodeStream.ARG_BYTE_COUNT - 1) {
                        buffer.append(' ');
                        buffer.append(ByteInt.intFromBytes(bytes));
                    }
                    ++i;
                }
            }

            buffer.append(App.NL);
        }
    }
}

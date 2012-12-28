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

import de.weltraumschaf.registermachine.Const;
import de.weltraumschaf.registermachine.bytecode.ByteCode;
import de.weltraumschaf.registermachine.bytecode.ByteCodeFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Disassembler {

    private static final String NL = String.format("%n");

    public String disassamble(final InputStream input) throws IOException {
        final StringBuilder buffer = new StringBuilder();
        final ByteCodeFile bc = new ByteCodeFile(input);

        if (!bc.isValid()) {
            throw new RuntimeException("Not a valid byte code file!");
        }

        buffer.append("; byte code version ").append(bc.getVersion()).append(NL);
        disassembleProgram(buffer, bc.getProgramm());
        return buffer.toString();
    }

    private void disassembleProgram(final StringBuilder buffer, final byte[] programm) {
        int i = 0;
        while (i < programm.length) {
            final ByteCode bc = ByteCode.lookup(programm[i]);

            if (ByteCode.UNKWONN == bc) {
                throw new RuntimeException(String.format("Unknown opcode: %s!", bc.toHex()));
            }

            buffer.append(bc.name().toLowerCase(Const.LOCALE));
            ++i;

            if (bc.getArgCount() != ByteCode.ArgCount.NONE) {
                for (int shift = 0; shift < bc.getArgCount().getCount(); ++shift) {
                    buffer.append(' ');
                    // TODO Read 4 bytes per argument!
                    buffer.append(programm[i]);
                    ++i;
                }
            }

            buffer.append(NL);
        }
    }
}

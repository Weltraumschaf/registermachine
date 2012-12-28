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
package de.weltraumschaf.registermachine.vm;

import com.google.common.collect.Lists;
import de.weltraumschaf.registermachine.Const;
import de.weltraumschaf.registermachine.bytecode.ByteCode;
import de.weltraumschaf.registermachine.bytecode.ByteCodeFile;
import de.weltraumschaf.registermachine.instr.Instruction;
import java.util.List;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Executor {

    private static final byte VERSION = (byte) 0x01;
    private final RegisterMachine machine;

    public Executor(final RegisterMachine machine) {
        this.machine = machine;
    }

    public void execute(final ByteCodeFile bc) {
        validateVersion(bc);
        final List<Instruction> instructions = generateInstructions(bc.getProgramm());
        machine.setProgram(instructions);
        machine.getConfiguration().init();
        machine.run();
    }

    private void validateVersion(ByteCodeFile bc) {
        if (VERSION < bc.getVersion()) {
            throw new IllegalArgumentException(String.format("Too new byte code ver. %d for executor ver. %d!", bc.getVersion(), VERSION));
        }
    }

    private List<Instruction> generateInstructions(byte[] programm) {
        final List<Instruction> instructions = Lists.newArrayList();
        int i = 0;
        while (i < programm.length) {
            final ByteCode bc = ByteCode.lookup(programm[i]);

            if (ByteCode.UNKWONN == bc) {
                throw new RuntimeException(String.format("Unknown opcode: %s!", bc.toHex()));
            }

            ++i;
            byte[] args;
            if (bc.getArgCount() == ByteCode.ArgCount.NONE) {
                args = new byte[0];
            }else {
                final int argCount = bc.getArgCount().getCount();
                args = new byte[argCount];
                for (int shift = 0; shift < argCount; ++shift) {

                    // TODO Read 4 bytes per argument!
                    args[shift] = programm[i];
                    ++i;
                }
            }

            final Instruction instr = createInstruction(bc, args);
            instructions.add(instr);
        }

        return instructions;
    }

    private Instruction createInstruction(final ByteCode bc, final byte[] args) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

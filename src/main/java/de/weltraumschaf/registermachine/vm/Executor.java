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
import de.weltraumschaf.registermachine.ByteInt;
import de.weltraumschaf.registermachine.bytecode.ByteCodeFile;
import de.weltraumschaf.registermachine.bytecode.OpCode;
import de.weltraumschaf.registermachine.instr.Add;
import de.weltraumschaf.registermachine.instr.Div;
import de.weltraumschaf.registermachine.instr.Instruction;
import de.weltraumschaf.registermachine.instr.Move;
import de.weltraumschaf.registermachine.instr.Mul;
import de.weltraumschaf.registermachine.instr.Nop;
import de.weltraumschaf.registermachine.instr.Sub;
import java.util.List;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Executor {

    private static final byte VERSION = (byte) 0x01;
    private static final int ARG_BYTE_COUNT = 4;
    private final RegisterMachine machine;

    public Executor(final RegisterMachine machine) {
        this.machine = machine;
    }

    public void execute(final ByteCodeFile bc) {
        validateVersion(bc);
        final List<Instruction> instructions = generateInstructions(bc.getProgramm());
        machine.setProgram(instructions);
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
            final OpCode bc = OpCode.lookup(programm[i]);

            if (OpCode.UNKWONN == bc) {
                throw new RuntimeException(String.format("Unknown opcode: %s!", bc.toHex()));
            }

            ++i;
            int[] args;
            if (bc.getArgCount() == OpCode.ArgCount.NONE) {
                args = new int[0];
            } else {
                final int argCount = bc.getArgCount().getCount();
                args = new int[argCount];
                final int byteCount = argCount * ARG_BYTE_COUNT;
                final byte[] bytes = new byte[ARG_BYTE_COUNT];
                int argI = 0;
                for (int shift = 0; shift < byteCount; ++shift) {
                    bytes[shift % ARG_BYTE_COUNT] = programm[i];

                    if (shift % ARG_BYTE_COUNT == ARG_BYTE_COUNT - 1) {
                        args[argI] = ByteInt.intFromBytes(bytes);
                        ++argI;
                    }

                    ++i;
                }
            }

            final Instruction instr = createInstruction(bc, args);
            instructions.add(instr);
        }

        return instructions;
    }

    void verifyArcCount(final OpCode bc, final int[] args) {
        if (args.length != bc.getArgCount().getCount()) {
            throw  new IllegalArgumentException(String.format("Opcode %s requires %d arguments!", bc.toString(), bc.getArgCount().getCount()));
        }
    }
    private Instruction createInstruction(final OpCode bc, final int[] args) {
        verifyArcCount(bc, args);
        Instruction instr = new Nop();

        switch (bc) {
            case MOVE:
                instr = new Move(args[0], args[1]);
                break;
            case ADD:
                instr = new Add(args[0], args[1], args[2]);
                break;
            case SUB:
                instr = new Sub(args[0], args[1], args[2]);
                break;
            case MUL:
                instr = new Mul(args[0], args[1], args[2]);
                break;
            case DIV:
                instr = new Div(args[0], args[1], args[2]);
                break;
            default:
                throw new IllegalArgumentException(String.format("Opcode nit implemented yet: %s!", bc.toString()));
        }

        return instr;
    }
}

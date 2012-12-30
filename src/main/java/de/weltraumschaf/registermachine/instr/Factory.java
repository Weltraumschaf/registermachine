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

import de.weltraumschaf.commons.IO;
import de.weltraumschaf.registermachine.bytecode.OpCode;

/**
 * Creates concrete instruction objects.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Factory {

    /**
     * Hide constructor for pure static class.
     */
    private Factory() {
        super();
    }

    /**
     * Verify that an instruction has correct number of arguments.
     *
     * @param bc opcode to verify for
     * @param args arguments
     * @throws IllegalArgumentException if, amount of arguments does not match
     */
    private static void verifyArcCount(final OpCode bc, final int[] args) {
        if (args.length != bc.getArgCount().getCount()) {
            throw new IllegalArgumentException(String.format("Opcode %s requires %d arguments!",
                                                             bc.toString(), bc.getArgCount().getCount()));
        }
    }

    /**
     * Creates instruction.
     *
     * @param bc opcode to create for
     * @param args args for instruction
     * @param io used for I/O
     * @return concrete instruction
     * @throws IllegalArgumentException if, amount of arguments does not match
     */
    public static Instruction createInstruction(final OpCode bc, final int[] args, final IO io) {
        verifyArcCount(bc, args);
        Instruction instr = new Nop();

        switch (bc) {
            case MOVE:
                instr = new Move(args[0], args[1]);
                break;
            case LOADC:
                instr = new Loadc(args[0], args[1]);
                break;
            case ADD:
                instr = new Add(args[0], args[1]);
                break;
            case SUB:
                instr = new Sub(args[0], args[1]);
                break;
            case MUL:
                instr = new Mul(args[0], args[1]);
                break;
            case DIV:
                instr = new Div(args[0], args[1]);
                break;
            case MOD:
                instr = new Mod(args[0], args[1]);
                break;
            case POW:
                instr = new Pow(args[0], args[1]);
                break;
            case UNM:
                instr = new Unm(args[0]);
                break;
            case NOT:
                instr = new Not(args[0]);
                break;
            case JMP:
                instr = new Jmp(args[0]);
                break;
            case EQ:
                instr = new Eq(args[0], args[1]);
                break;
            case LT:
                instr = new Lt(args[0], args[1]);
                break;
            case LE:
                instr = new Le(args[0], args[1]);
                break;
            case TEST:
                instr = new Test(args[0], args[1]);
                break;
            case PRINT:
                instr = new Print(args[0], io);
                break;
            case PRINTLN:
                instr = new PrintLn(args[0], io);
                break;
            default:
                throw new IllegalArgumentException(String.format("Opcode not implemented yet: %s!", bc.toString()));
        }

        return instr;
    }

}

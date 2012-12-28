/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 42):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a beer in return.
 *
 */

package de.weltraumschaf.registermachine.vm;

import de.weltraumschaf.commons.IO;
import de.weltraumschaf.registermachine.Const;
import de.weltraumschaf.registermachine.instr.Instruction;
import java.util.List;

public class RegisterMachine {

    private final RuntimeConfiguration config;
    private final boolean debug;
    private final boolean printProgramm;
    private final IO io;

    private List<Instruction> program;

    public  RegisterMachine(final IO io) {
        this(io, false, false);
    }

    public  RegisterMachine(final IO io, boolean debug, boolean printProgramm) {
        super();
        this.io = io;
        this.config = new RuntimeConfiguration();
        this.debug  = debug;
        this.printProgramm  = printProgramm;
    }

    public void setProgram(final List<Instruction> prog) {
        program = prog;
    }

    public void run() {
        final StringBuilder debugOutput = new StringBuilder();

        if (debug) {
            debugOutput.append(config.toString()).append(Const.NL);
        }

        if (!program.isEmpty()) {
            while ( config.getInstructionCounter() < program.size()) {
                final Instruction instruction = program.get(config.getInstructionCounter());
                instruction.evaluate(config);

                if (debug) {
                    debugOutput.append(config.toString()).append(Const.NL);
                }
            }
        }

        if (debug) {
            io.println(String.format("%nDebug:%n%s", debugOutput.toString()));
        }

        if (this.printProgramm) {
            this.printProgramm();
        }
    }

    public void printProgramm() {
        io.println("\nProgramm:\n");

        for (final Instruction instruction : program) {
            io.println(instruction.toString());
        }
    }

    public void printConfiguration() {
        io.println(config.toString());
    }

    public RuntimeConfiguration getConfiguration() {
        return this.config;
    }

}
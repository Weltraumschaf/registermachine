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

import de.weltraumschaf.registermachine.Const;
import de.weltraumschaf.registermachine.instr.Instruction;
import java.util.List;

public class RegisterMachine {

    private final RuntimeConfiguration config;
    private List<Instruction> program;
    private final boolean debug;
    private final boolean printProgramm;

    public  RegisterMachine() {
        this(false, false);
    }

    public  RegisterMachine(boolean debug, boolean printProgramm) {
        this.config = new RuntimeConfiguration(4);
        this.debug  = debug;
        this.printProgramm  = printProgramm;
    }

    public void setProgram(final List<Instruction> prog) {
        program = prog;
    }

    public void run() {
        final StringBuilder debugOutput = new StringBuilder();

        if (debug) {
            debugOutput.append(config.toString()).append(String.format("%n"));
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
            System.out.println(String.format("%nDebug:%n%s", debugOutput.toString()));
        }

        if (this.printProgramm) {
            this.printProgramm();
        }
    }

    public void printProgramm() {
        System.out.println("\nProgramm:\n");

        if (!this.printProgramm) {
            System.out.println("empty programm");
            return;
        }

        for (final Instruction instruction : program) {
            System.out.println(instruction.toString());
        }
    }

    public void printConfiguration() {
        System.out.println(config.toString());
    }

    public RuntimeConfiguration getConfiguration() {
        return this.config;
    }

}
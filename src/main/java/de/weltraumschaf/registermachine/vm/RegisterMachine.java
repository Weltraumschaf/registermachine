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
import de.weltraumschaf.registermachine.App;
import de.weltraumschaf.registermachine.instr.Instruction;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * Implements the register based virtual machine.
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
public class RegisterMachine {
    private static final int DEBUG_PAD = 26;

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
        config.setProgramCounter(0);
        debugInit(debugOutput);

        if (!program.isEmpty()) {
            while (config.getProgramCounter() < program.size()) {
                final Instruction instruction = program.get(config.getProgramCounter());
                config.incrementProgramCounter();
                instruction.evaluate(config);
                debugInstruction(debugOutput, instruction);
            }
            io.println("HALT.");
            io.println("");
        }

        if (debug) {
            io.println(String.format("Debug:%n======%n%s", debugOutput.toString()));
        }

        if (this.printProgramm) {
            this.printProgramm();
        }
    }

    public void printProgramm() {
        io.println(String.format("Programm:%n========="));

        for (final Instruction instruction : program) {
            io.println(instruction.toString());
        }

        io.print(App.NL);
    }

    public void printConfiguration() {
        io.println(config.toString());
    }

    public RuntimeConfiguration getConfiguration() {
        return this.config;
    }

    private void debugInit(final StringBuilder debugOutput) {
        if (debug) {
            debugOutput.append(StringUtils.rightPad("Init", DEBUG_PAD))
                       .append(" > ")
                       .append(config.toString())
                       .append(App.NL);
        }
    }

    private void debugInstruction(final StringBuilder debugOutput, final Instruction instruction) {
        if (debug) {
            debugOutput.append(StringUtils.rightPad("Instruction '" + instruction.toString() + "'", DEBUG_PAD))
                       .append(" > ")
                       .append(config.toString())
                       .append(App.NL);
        }
    }

}

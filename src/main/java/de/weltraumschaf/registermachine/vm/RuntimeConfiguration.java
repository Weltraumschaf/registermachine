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

import de.weltraumschaf.registermachine.instr.Instruction;
import de.weltraumschaf.registermachine.typing.Value;
import java.util.Stack;

/**
 * Runtime environment configuration.
 *
 * Manages a stack of scopes and a program counter.
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
public class RuntimeConfiguration {

    /**
     * Stack of scopes with the current scope at the top.
     */
    private final Stack<Scope> scopes = new Stack<Scope>();
    /**
     * Program counter.
     */
    private int programCounter;

    /**
     * Pushes initial scope onto scopes stack.
     */
    public RuntimeConfiguration() {
        super();
        scopes.push(new Scope());
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(final int ic) {
        this.programCounter = ic;
    }

    public void incrementProgramCounter() {
        programCounter++;
    }

    public void setRegisterA(final Value v) {
        setRegister(Instruction.REG_A, v);
    }

    public void setRegisterB(final Value v) {
        setRegister(Instruction.REG_B, v);
    }

    public void setRegisterC(final Value v) {
        setRegister(Instruction.REG_C, v);
    }

    public void setRegister(final int r, final Value v) {
        scopes.peek().getRegisters().set(r, v);
    }

    public Value getRegisterA() {
        return getRegister(Instruction.REG_A);
    }

    public Value getRegisterB() {
        return getRegister(Instruction.REG_B);
    }

    public Value getRegisterC() {
        return getRegister(Instruction.REG_C);
    }

    public Value getRegister(final int r) {
        return scopes.peek().getRegisters().get(r);
    }

    public void assignConstant(final Value v) {
        scopes.peek().getConstants().assign(v);
    }

    public boolean lookupConstant(final int index) {
        return scopes.peek().getConstants().lookup(index);
    }

    public Value retireveConstant(final int index) {
        return scopes.peek().getConstants().retrieve(index);
    }

    public void assignVariable(final Value v) {
        scopes.peek().getVariables().assign(v);
    }

    public boolean lookupVariable(final int index) {
        return scopes.peek().getVariables().lookup(index);
    }

    public Value retireveVariable(final int index) {
        return scopes.peek().getVariables().retrieve(index);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Counter=")
                .append(programCounter)
                .append(" Registers=")
                .append(scopes.peek().getRegisters());
        return sb.toString();
    }

}

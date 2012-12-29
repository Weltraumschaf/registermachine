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

import de.weltraumschaf.registermachine.typing.Value;
import java.util.Stack;

public class RuntimeConfiguration {

    private final Stack<Scope> scopes = new Stack<Scope>();

    private Scope currentScope;
    private int programCounter;

    public RuntimeConfiguration() {
        super();
        currentScope = new Scope();
        scopes.push(currentScope);
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

    public void setRegister(final int r, final Value v) {
        currentScope.getRegisters().set(r, v);
    }

    public Value getRegister(final int r) {
        return currentScope.getRegisters().get(r);
    }

    public void assignConstant(final Value v) {
        currentScope.getConstants().assign(v);
    }

    public boolean lookupConstant(final int index) {
        return currentScope.getConstants().lookup(index);
    }

    public Value retireveConstant(final int index) {
        return currentScope.getConstants().retrieve(index);
    }

    public void assignVariable(final Value v) {
        currentScope.getVariables().assign(v);
    }

    public boolean lookupVariable(final int index) {
        return currentScope.getVariables().lookup(index);
    }

    public Value retireveVariable(final int index) {
        return currentScope.getVariables().retrieve(index);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Counter=")
          .append(programCounter)
          .append(" Registers=")
          .append(currentScope.getRegisters());
        return sb.toString();
    }

}
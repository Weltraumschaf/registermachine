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

package de.weltraumschaf.registermachine.typing;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import java.util.List;

/**
 * Describes a function.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Function {

    /**
     * Contains function code.
     */
    private final List<Code> code = Lists.newArrayList();
    /**
     * Contains local variables.
     */
    private final List<Value> variables = Lists.newArrayList();
    /**
     * Contains local contains.
     */
    private final List<Value> constants = Lists.newArrayList();
    /**
     * Contains nested functions.
     */
    private final List<Function> functions = Lists.newArrayList();
    /**
     * Number of up values.
     */
    private final int nups;
    /**
     * Number of parameters.
     */
    private final int numparams;
    /**
     * Var arg description.
     *
     * TODO Remove this.
     */
    private final int isVararg;
    /**
     * Max stack size.
     */
    private final int maxStackSize;

    public Function(int nups, int numparams, int isVararg, int maxstacksize) {
        super();
        this.nups = nups;
        this.numparams = numparams;
        this.isVararg = isVararg;
        this.maxStackSize = maxstacksize;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .add("nups", nups)
                      .add("numparams", numparams)
                      .add("isVararg", isVararg)
                      .add("maxStackSize", maxStackSize)
                      .add("code", code)
                      .toString();
    }

    public List<Code> getCode() {
        return code;
    }

    public void addCode(final Code c) {
        code.add(c);
    }

    public int getNups() {
        return nups;
    }

    public int getNumparams() {
        return numparams;
    }

    public int getIsVararg() {
        return isVararg;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public void addVariable(final Value v) {
        variables.add(v);
    }

    public Value getVariable(final int index) {
        return variables.get(index);
    }

    public void addConstant(final Value v) {
        constants.add(v);
    }

    public Value getConstant(final int index) {
        return constants.get(index);
    }



}

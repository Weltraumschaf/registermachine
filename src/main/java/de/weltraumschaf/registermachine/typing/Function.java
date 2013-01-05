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
import com.google.common.primitives.Bytes;
import de.weltraumschaf.registermachine.convert.ByteValue;
import java.io.UnsupportedEncodingException;
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
    private final List<Code> functionCode = Lists.newArrayList();
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
    private final byte nups;
    /**
     * Number of parameters.
     */
    private final byte numparams;
    /**
     * Var arg description.
     *
     * TODO Remove this.
     */
    private final byte isVararg;
    /**
     * Max stack size.
     */
    private final byte maxStackSize;

    public Function(byte nups, byte numparams, byte isVararg, byte maxstacksize) {
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
                .add("code", functionCode)
                .toString();
    }

    public List<Code> getCode() {
        return functionCode;
    }

    public void addCode(final Code c) {
        functionCode.add(c);
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

    public List<Byte> asByteList() throws UnsupportedEncodingException {
        final List<Byte> bytes = Lists.newArrayList();
        bytes.add(Byte.valueOf(nups));
        bytes.add(Byte.valueOf(numparams));
        bytes.add(Byte.valueOf(isVararg));
        bytes.add(Byte.valueOf(maxStackSize));

        bytes.add((byte) functionCode.size()); // XXX Use 32 bit int
        for (final Code instruction : functionCode) {
            bytes.addAll(instruction.asByteList());
        }

        bytes.add((byte) constants.size()); // XXX Use 32 bit int
        for (final Value constant : constants) {
            bytes.addAll(Bytes.asList(ByteValue.bytesFromValue(constant)));
        }

        bytes.add((byte) variables.size()); // XXX Use 32 bit int
        for (final Value variable : variables) {
            bytes.addAll(Bytes.asList(ByteValue.bytesFromValue(variable)));
        }

        bytes.add((byte) functions.size()); // XXX Use 32 bit int
        if (!functions.isEmpty()) {
            // TODO Implement code generation
        }

        return bytes;
    }
}

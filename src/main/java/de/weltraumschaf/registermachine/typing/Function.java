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
import de.weltraumschaf.registermachine.instr.Instruction;
import java.util.List;

/**
 * Describes a function.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Function {

    private final List<Instruction> code = Lists.newArrayList();
    private final int nups;
    private final int numparams;
    private final int isVararg;
    private final int maxStackSize;

    public Function(int nups, int numparams, int is_vararg, int maxstacksize) {
        super();
        this.nups = nups;
        this.numparams = numparams;
        this.isVararg = is_vararg;
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

    public List<Instruction> getCode() {
        return code;
    }

}

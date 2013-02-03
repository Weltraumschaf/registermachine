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

package de.weltraumschaf.registermachine.inter;

import com.google.common.collect.Lists;
import java.util.List;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class FunctionNode extends AbstractNode {

    private final List<VarNode> variables = Lists.newArrayList();

    private FunctionNode() {
        super(AstNode.Type.FUNCTION);
    }

    public static FunctionNode newFunctionNode() {
        return new FunctionNode();
    }

    public void addVariable(final VarNode var) {
        variables.add(var);
    }

    public List<VarNode> getVariables() {
        return Lists.newArrayList(variables);
    }

}

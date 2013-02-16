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
 * Represents a parsed function.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class FunctionNode extends AbstractNode {

    /**
     * Holds all variables declared in the scope of this function.
     */
    private final List<VarNode> variables = Lists.newArrayList();
    /**
     * Holds all constants declared in the scope of this function.
     */
    private final List<ConstNode> constants = Lists.newArrayList();
    /**
     * Holds all statements in the scope of this function.
     */
    private final List<StatementNode> statements = Lists.newArrayList();

    /**
     * Hidden to enforce usage of {@link #newFunctionNode() factory method}.
     */
    private FunctionNode() {
        super(Type.FUNCTION);
    }

    /**
     * Factory method.
     *
     * @return new instance
     */
    static FunctionNode newFunctionNode() {
        return new FunctionNode();
    }

    /**
     * Add a variable to the function.
     *
     * Adding means in this context that the variable is declared in the lexical scope of this function.
     *
     * TODO Should throw exception on redeclaration
     * @param var the variable node
     */
    public void addVariable(final VarNode var) {
        variables.add(var);
    }

    /**
     * Returns a list of all variables.
     *
     * @return defense copy of the list
     */
    public List<VarNode> getVariables() {
        return Lists.newArrayList(variables);
    }

    /**
     * Add a constants to the function.
     *
     * Adding means in this context that the variable is declared in the lexical scope of this function.
     *
     * TODO Should throw exception on redeclaration
     * @param constant the constant node
     */
    public void addConstant(final ConstNode constant) {
        constants.add(constant);
    }

    /**
     * Returns a list of all constants.
     *
     * @return defense copy of the list
     */
    public List<ConstNode> getConstants() {
        return Lists.newArrayList(constants);
    }

    public void addStatements(final StatementNode statement) {
        statements.add(statement);
    }

    public List<StatementNode> getStatements() {
        return Lists.newArrayList(statements);
    }

}

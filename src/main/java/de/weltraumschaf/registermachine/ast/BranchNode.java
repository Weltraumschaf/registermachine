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

package de.weltraumschaf.registermachine.ast;

import com.google.common.collect.Lists;
import java.util.List;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class BranchNode extends AbstractNode {

    private ExpressionNode condition;
    private final List<AstNode> ifStatements = Lists.newArrayList();
    private final List<AstNode> elseStatements = Lists.newArrayList();

    void setCondition(final ExpressionNode condition) {
        this.condition = condition;
    }

    void addIfStatementNode(final AstNode n) {
        ifStatements.add(n);
    }

    void addElseStatementNode(final AstNode n) {
        elseStatements.add(n);
    }

}

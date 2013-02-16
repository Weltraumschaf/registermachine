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

/**
 * Represents a parsed assignment.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class AssignmentNode extends StatementNode {

    private ValueNode leftHandSide;
    private ExpressionNode rightHandSide;

    /**
     * Hidden to enforce usage of {@link #newAssignmentNode() factory method}.
     */
    private AssignmentNode() {
        super(Type.ASSIGNMENT);
    }

    /**
     * Factory method.
     *
     * @return new instance
     */
    static AssignmentNode newAssignmentNode() {
        return new AssignmentNode();
    }

    public ValueNode getLeftHandSide() {
        return leftHandSide;
    }

    public void setLeftHandSide(final ValueNode leftHandSide) {
        this.leftHandSide = leftHandSide;
    }

    public ExpressionNode getRightHandSide() {
        return rightHandSide;
    }

    public void setRightHandSide(final ExpressionNode rightHandSide) {
        this.rightHandSide = rightHandSide;
    }

}

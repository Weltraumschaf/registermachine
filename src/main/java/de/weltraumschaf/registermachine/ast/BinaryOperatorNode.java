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

import de.weltraumschaf.registermachine.front.Operator;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class BinaryOperatorNode {

    private Operator operator;
    private ExpressionNode leftHandSide;
    private ExpressionNode rightHandSide;

    public void setOperator(final Operator operator) {
        this.operator = operator;
    }

    public void setLeftHandSide(final ExpressionNode leftHandSide) {
        this.leftHandSide = leftHandSide;
    }

    public void setRightHandSide(final ExpressionNode rightHandSide) {
        this.rightHandSide = rightHandSide;
    }

}

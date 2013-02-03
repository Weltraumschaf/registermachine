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
package de.weltraumschaf.registermachine.front;

import de.weltraumschaf.commons.token.Token;
import de.weltraumschaf.commons.token.TokenType;
import de.weltraumschaf.registermachine.inter.FunctionNode;
import de.weltraumschaf.registermachine.inter.Nodes;
import de.weltraumschaf.registermachine.inter.Value;

/**
 * Parse source and generates an AST.
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
final class Parser {

    /**
     * Factory to create AST nodes.
     */
    private final Nodes nodeFactory = new Nodes();
    /**
     * Does lexical analysis of input source.
     */
    private final Scanner scanner;
    /**
     * Root of the AST.
     */
    private final FunctionNode mainFunction;

    private Parser(final Scanner scanner) {
        super();
        this.scanner = scanner;
        this.mainFunction = nodeFactory.newFunctionNode();
    }

    static Parser forString(final String string) {
        return new Parser(Scanner.forString(string));
    }

    FunctionNode getAbstractSyntaxtTree() {
        return mainFunction;
    }

    void parse() {
        while(scanner.hasNext()) {
            final Token token = scanner.getCurrentToken();

            if (token.getType() == TokenType.KEYWORD) {
                parseKeyword();
            }
        }
    }

    private void parseKeyword() {
        final Token<String> currentToken = (Token<String>) scanner.getCurrentToken();
        final Keyword keyword = Keyword.forValue(currentToken.getValue());

        switch (keyword) {
            case VAR:
                parseVariables();
                break;
            default:
                throw new SyntaxException(String.format("Unknown keyword '%s'!", keyword.getLiteral()));
        }
    }

    private void parseVariables() {
        if (!scanner.hasNext()) {
            throw new SyntaxException("Unexpected end of source!");
        }

        scanner.next(); // consume var keyword

        if (isOperator(scanner.getCurrentToken(), "{")) {
            parseVariableList();
        } else {
            parseVariable();
        }
    }

    private void parseVariableList() {
        scanner.next(); // consume {

        while (scanner.hasNext()) {
            if (isOperator(scanner.getCurrentToken(), "}")) {
                if (scanner.hasNext()) {
                    scanner.next(); // consume }
                }
                break;
            }
            parseVariable();
        }
    }

    private void parseVariable() {
        final Token nameToken = scanner.getCurrentToken();

        if (nameToken.getType() != TokenType.LITERAL) {
            throw new SyntaxException("Identifier expected!");
        }

        final String variableName = ((Token<String>) nameToken).getValue();
        scanner.next(); // consume name

        final Token maybeAssign = scanner.getCurrentToken();
        Value value;
        if (isOperator(maybeAssign, "=")) {
            if (!scanner.hasNext()) {
                throw new SyntaxException("Unexpected end of source!");
            }

            scanner.next(); // consume =
            final Token valueToken = scanner.getCurrentToken();

            switch (valueToken.getType()) {
                case NULL:
                    value = Value.getNil();
                    break;
                case BOOLEAN:
                    if (((Token<Boolean>) valueToken).getValue()) {
                        value = Value.getTrue();
                    } else {
                        value = Value.getFalse();
                    }
                    break;
                case FLOAT:
                    value = Value.valueOf(((Token<Float>) valueToken).getValue());
                    break;
                case INTEGER:
                    value = Value.valueOf(((Token<Integer>) valueToken).getValue());
                    break;
                case STRING:
                    value = Value.valueOf(((Token<String>) valueToken).getValue());
                    break;
                default:
                    throw new SyntaxException(String.format("Bad value '%s' assigned to varibale '%s'!",
                                                            valueToken.getValue(), variableName));
            }
        } else {
            value = Value.getNil();
        }


        mainFunction.addVariable(nodeFactory.newVarNode(variableName, value));
    }

    private boolean isOperator(final Token token, final String literal) {
        return token.getType() == TokenType.OPERATOR && ((Token<String>) token).getValue().equals(literal);
    }

}

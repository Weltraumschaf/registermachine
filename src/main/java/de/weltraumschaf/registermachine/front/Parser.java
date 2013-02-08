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
import static de.weltraumschaf.registermachine.front.Keyword.CONST;
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
        while (scanner.hasNext()) {
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
            case CONST:
                parseConstants();
                break;
            default:
                throw new SyntaxException(String.format("Unknown keyword '%s'!", keyword.getLiteral()));
        }
    }

    private void parseVariables() {
        parseValues(false);
    }

    private void parseConstants() {
        parseValues(true);
    }

    private void parseValues(final boolean isConstant) {
        if (!scanner.hasNext()) {
            throw new SyntaxException("Unexpected end of source!");
        }

        scanner.next(); // consume var/const keyword

        if (isOperator(scanner.getCurrentToken(), "{")) {
            parseValuesList(isConstant);
        } else {
            parseValue(isConstant);
        }
    }

    private void parseValuesList(final boolean isConstant) {
        scanner.next(); // consume {

        if (TokenType.EOL != scanner.getCurrentToken().getType()) {
            throw new SyntaxException("Missing new line after {!");
        }
        scanner.next(); // consume new line

        while (scanner.hasNext()) {
            if (isOperator(scanner.getCurrentToken(), "}")) {
                if (scanner.hasNext()) {
                    scanner.next(); // consume }
                }
                break;
            }
            parseValue(isConstant);
        }
    }

    private void parseValue(final boolean isConstant) {
        final Token nameToken = scanner.getCurrentToken();

        if (nameToken.getType() != TokenType.LITERAL) {
            throw new SyntaxException("Identifier expected!");
        }

        final String name = ((Token<String>) nameToken).getValue();
        scanner.next(); // consume name
        final Token maybeAssign = scanner.getCurrentToken();
        Value value;

        if (isOperator(maybeAssign, "=")) {
            if (!scanner.hasNext()) {
                throw new SyntaxException("Unexpected end of source!");
            }

            scanner.next(); // consume =
            value = determineTypedValue(scanner.getCurrentToken(), name);
            scanner.next(); // consume value
        } else {
            value = Value.getNil();
        }

        if (TokenType.EOL != scanner.getCurrentToken().getType()) {
            throw new SyntaxException("Missing new line after var declaration!");
        }

        scanner.next(); // consume new line

        if (isConstant) {
            mainFunction.addConstant(nodeFactory.newConstNode(name, value));
        } else {
            mainFunction.addVariable(nodeFactory.newVarNode(name, value));
        }
    }

    private Value determineTypedValue(final Token valueToken, final String variableName) {
        switch (valueToken.getType()) { // NOPMD
            case NULL:
                return Value.getNil();
            case BOOLEAN:
                if (((Token<Boolean>) valueToken).getValue()) {
                    return Value.getTrue();
                } else {
                    return Value.getFalse();
                }
            case FLOAT:
                return Value.valueOf(((Token<Float>) valueToken).getValue());
            case INTEGER:
                return Value.valueOf(((Token<Integer>) valueToken).getValue());
            case STRING:
                return Value.valueOf(((Token<String>) valueToken).getValue());
            default:
                throw new SyntaxException(String.format("Bad value '%s' assigned to varibale '%s'!",
                                                        valueToken.getValue(), variableName));
        }
    }

    private boolean isOperator(final Token token, final String literal) {
        return token.getType() == TokenType.OPERATOR && ((Token<String>) token).getValue().equals(literal);
    }

}

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
import de.weltraumschaf.registermachine.ast.FunctionNode;
import de.weltraumschaf.registermachine.ast.SymbolNode;
import de.weltraumschaf.registermachine.inter.Nodes;
import de.weltraumschaf.registermachine.inter.Value;
import org.apache.commons.lang.Validate;

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
        this.mainFunction = new FunctionNode();
        this.mainFunction.setName(new SymbolNode("__main__"));
    }

    static Parser forString(final String string) {
        return new Parser(Scanner.forString(string));
    }

    FunctionNode getAbstractSyntaxtTree() {
        return mainFunction;
    }

    void parse() {
        while (scanner.hasNext()) {
            final Token<?> token = scanner.getCurrentToken();
        }
    }

    static Value determineTypedValue(final Token<?> valueToken, final String variableName) {
        Validate.notNull(valueToken);
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

    static boolean isOperator(final Token<?> token, final String literal) {
        Validate.notNull(token);
        Validate.notNull(literal);
        Validate.notEmpty(literal);
        return token.getType() == TokenType.OPERATOR && literal.equals(((Token<String>) token).getValue());
    }

}

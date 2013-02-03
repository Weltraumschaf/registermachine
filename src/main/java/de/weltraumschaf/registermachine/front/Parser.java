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
import de.weltraumschaf.registermachine.inter.AstNode;
import de.weltraumschaf.registermachine.inter.NopNode;
import de.weltraumschaf.registermachine.inter.Value;
import de.weltraumschaf.registermachine.inter.VarNode;

/**
 * Parse source and generates an AST.
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
final class Parser {

    private final Scanner scanner;
    private AstNode ast;

    private Parser(final Scanner scanner) {
        super();
        this.scanner = scanner;
        this.ast = NopNode.newNopNode();
    }

    static Parser forString(final String string) {
        return new Parser(Scanner.forString(string));
    }

    AstNode getAbstractSyntaxtTree() {
        return ast;
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
                parseVariable();
                break;
            default:
                throw new SyntaxException(String.format("Unknown keyword '%s'!", keyword.getLiteral()));
        }
    }

    private void parseVariable() {
        if (!scanner.hasNext()) {
            throw new SyntaxException("Unexpected end of source!");
        }

        scanner.next(); // consume var keyword

        final Token name = scanner.getCurrentToken();

        if (name.getType() != TokenType.LITERAL) {
            throw new SyntaxException("Identifier expected!");
        }

        scanner.next(); // consume name
        ast = VarNode.newVarNode(((Token<String>) name).getValue(), Value.getNil());
    }

}

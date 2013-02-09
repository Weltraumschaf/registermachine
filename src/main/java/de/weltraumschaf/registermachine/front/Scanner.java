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

import com.google.common.base.Objects;
import de.weltraumschaf.commons.characters.CharacterHelper;
import de.weltraumschaf.commons.characters.CharacterStream;
import de.weltraumschaf.commons.token.Position;
import de.weltraumschaf.commons.token.Token;
import de.weltraumschaf.commons.token.TokenType;
import de.weltraumschaf.commons.token.Tokens;

/**
 * Scans the source code and produce tokens.
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
final class Scanner {

    /**
     * Source input.
     */
    private final CharacterStream input;
    /**
     * Current recognized token.
     */
    private Token<?> currentToken;
    private int line = 1;
    private int columnOffset = 0;

    /**
     * Hidden to enforce creation by {@link #forString(java.lang.String) factory method}.
     *
     * @param input source input
     */
    private Scanner(final CharacterStream input) {
        super();
        this.input = input;
    }

    /**
     * Factory method to create scanner from source string.
     *
     * @param string source input
     * @return a newly created scanner
     */
    static Scanner forString(final String string) {
        return new Scanner(new CharacterStream(string));
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("currentToken", currentToken).toString();
    }

    /**
     * Create position of current scanned character.
     *
     * @return new instance
     */
    Position createPosition() {
        return new Position(line, input.getIndex() - columnOffset + 1);
    }

    /**
     * Return the current recognized token.
     *
     * If never {@link #next()} was invoked this method will invoke it. If the source is empty or at the end
     * an EOF token is returned.
     *
     * @return current scanned token
     */
    Token<?> getCurrentToken() {
        if (null == currentToken) {
            next();
        }

        return currentToken;
    }

    /**
     * Tests if there are more tokens.
     *
     * @return {@code true} if there are more tokens, else {@code false}
     */
    boolean hasNext() {
        if (null == currentToken) {
            return true;
        }
        return TokenType.EOF != currentToken.getType();
    }

    /**
     * Scans the next token.
     */
    void next() {
        while (input.hasNext()) {
            final char currentCharacter = input.next();

            // Must be befor whitespace check, because \n is also whitespace
            if ('\n' == currentCharacter) {
                currentToken = Tokens.newEndOfLineToken(createPosition());
                columnOffset = input.getIndex() + 1;
                ++line;
                return;
            }

            if (CharacterHelper.isWhiteSpace(currentCharacter)) {
                continue; // ignore white spaces
            }

            if (CharacterHelper.isAlpha(currentCharacter)) {
                scanKeywordOrLiteral();
                return;
            }

            if (CharacterHelper.isNum(currentCharacter)) {
                scanNumber();
                return;
            }

            if (CharacterHelper.isDoubleQuote(currentCharacter)) {
                scanString();
                return;
            }

            if ('/' == currentCharacter) {
                scanCommentOrOperator();
                return;
            }

            if (CharacterHelper.isOperator(currentCharacter)) {
                scanOperator();
                return;
            }
        }
        currentToken = Tokens.newEndOfFileToken(createPosition());
    }

    /**
     * Scans comments or operator starting with '/'.
     */
    private void scanCommentOrOperator() {
        final char peekedCharacter = input.peek();

        if ('/' == peekedCharacter) {
            scanSingleLineComment();
        } else if ('*' == peekedCharacter) {
            scanMultiLineComment();
        } else {
            scanOperator();
        }
    }

    /**
     * Scan single line comments beginning with "//".
     */
    private void scanSingleLineComment() {
        final StringBuilder buffer = new StringBuilder();
        final Position pos = createPosition();
        buffer.append(input.current()); // consume first slash
        input.next();
        buffer.append(input.current()); // consume second slash

        while (input.hasNext()) {
            final char currentCharacter = input.next();
            if ('\n' == currentCharacter) {
                break;
            }
            buffer.append(currentCharacter);
        }

        currentToken = Tokens.newCommentToken(buffer.toString(), pos);
    }

    /**
     * Scan multi line comment starting with "/*" and ending star slash.
     */
    private void scanMultiLineComment() {
        final StringBuilder buffer = new StringBuilder();
        final Position pos = createPosition();
        buffer.append(input.current()); // consume slash
        input.next();
        buffer.append(input.current()); // consume star

        while (input.hasNext()) {
            final char currentCharacter = input.next();

            if ('*' == currentCharacter) {
                buffer.append(currentCharacter);

                if (input.hasNext() && '/' == input.next()) {
                    buffer.append(input.current());
                    if (input.hasNext()) {
                        input.next(); // consume slash
                    }

                    currentToken = Tokens.newCommentToken(buffer.toString(), pos);
                    return;
                }

                throw new SyntaxException("Unterminated multiline comment!");
            }

            buffer.append(currentCharacter);
        }

        throw new SyntaxException("Unterminated multiline comment!");
    }

    /**
     * Scan operators.
     */
    private void scanOperator() {
        final StringBuilder buffer = new StringBuilder();
        final Position pos = createPosition();
        final char currentChar = input.current();
        buffer.append(currentChar);

        switch (currentChar) {
            case '!':
                if (input.hasNext() && input.peek() == '=') {
                    buffer.append(input.next());
                }
                break;
            case '&':
                if (input.hasNext() && input.peek() == '&') {
                    buffer.append(input.next());
                }
                break;
            case '|':
                if (input.hasNext() && input.peek() == '|') {
                    buffer.append(input.next());
                }
                break;
            case '=':
                if (input.hasNext() && (input.peek() == '=' || input.peek() == '>')) {
                    buffer.append(input.next());
                }
                break;
            case '<':
                if (input.hasNext() && input.peek() == '=') {
                    buffer.append(input.next());
                }
                break;
            default:
                // nothing to do
        }

        currentToken = Tokens.newOperatorToken(buffer.toString(), pos);
    }

    /**
     * Scan keywords or literals.
     */
    private void scanKeywordOrLiteral() {
        final StringBuilder buffer = new StringBuilder();
        final Position pos = createPosition();
        buffer.append(input.current());

        while (input.hasNext()) {
            if (!CharacterHelper.isAlphaNum(input.peek())) {
                break;
            }

            buffer.append(input.next());
        }

        determineKeywordOrLiteralToken(buffer.toString(), pos);
    }

    /**
     * Determines if a literal is a keyword or not.
     *
     * @param literal literal to examine
     */
    private void determineKeywordOrLiteralToken(final String literal, final Position pos) {
        if (Keyword.isKeyword(literal)) {
            currentToken = Tokens.newKeywordToken(literal, pos);
        } else if ("true".equals(literal)) {
            currentToken = Tokens.newBooleanToken(Boolean.TRUE, pos);
        } else if ("false".equals(literal)) {
            currentToken = Tokens.newBooleanToken(Boolean.FALSE, pos);
        } else if ("nil".equals(literal)) {
            currentToken = Tokens.newNullToken(pos);
        } else {
            currentToken = Tokens.newLiteralToken(literal, pos);
        }
    }

    /**
     * Scan string.
     */
    private void scanString() {
        final StringBuilder buffer = new StringBuilder();
        final Position pos = createPosition();

        while (input.hasNext()) {
            if ('"' == input.next()) {
                currentToken = Tokens.newStringToken(buffer.toString(), pos);
                return;
            } else {
                buffer.append(input.current());
            }
        }

        throw new SyntaxException("Unterminated string!");
    }

    /**
     * Scan float or integer numbers.
     */
    private void scanNumber() {
        final StringBuilder buffer = new StringBuilder();
        final Position pos = createPosition();
        buffer.append(input.current());
        boolean isFloat = false;

        while (input.hasNext()) {
            if (!CharacterHelper.isNum(input.peek()) && '.' != input.peek()) {
                break;
            }

            final char currentChar = input.next();

            if ('.' == currentChar) {
                isFloat = true;
                buffer.append(currentChar);
                continue;
            }

            buffer.append(currentChar);
        }

        if (isFloat) {
            currentToken = Tokens.newFloatToken(Float.valueOf(buffer.toString()), pos);
        } else {
            currentToken = Tokens.newIntegerToken(Integer.valueOf(buffer.toString()), pos);
        }
    }

}

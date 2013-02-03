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

import de.weltraumschaf.commons.characters.CharacterHelper;
import de.weltraumschaf.commons.characters.CharacterStream;
import de.weltraumschaf.commons.token.Token;

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
    private Token<?> currentToken = null;

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
        return input.hasNext();
    }

    /**
     * Scans the next token.
     */
    void next() {
        while (input.hasNext()) {
            final char currentCharacter = input.current();

            if (CharacterHelper.isWhiteSpace(currentCharacter)) {
                input.next(); // consume whitespace
                continue;
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
        currentToken = Token.newEndOfFileToken();
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

        currentToken = Token.newCommentToken(buffer.toString());
    }

    /**
     * Scan multi line comment starting with "/*" and ending star slash.
     */
    private void scanMultiLineComment() {
        final StringBuilder buffer = new StringBuilder();
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
                    currentToken = Token.newCommentToken(buffer.toString());
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
        final char currentChar = input.current();
        buffer.append(currentChar);
        input.next(); // consume operator character

        switch (currentChar) {
            case '!':
                if (input.hasNext() && input.current() == '=') {
                    buffer.append(input.current());
                    input.next(); // consume =
                }
                break;
            case '&':
                if (input.hasNext() && input.current() == '&') {
                    buffer.append(input.current());
                    input.next(); // consume &
                }
                break;
            case '|':
                if (input.hasNext() && input.current() == '|') {
                    buffer.append(input.current());
                    input.next(); // consume |
                }
                break;
            case '=':
                if (input.hasNext() && (input.current() == '=' || input.current() == '>')) {
                    buffer.append(input.current());
                    input.next(); // consume = or >
                }
                break;
            case '<':
                if (input.hasNext() && input.current() == '=') {
                    buffer.append(input.current());
                    input.next(); // consume <
                }
                break;

        }

        currentToken = Token.newOperatorToken(buffer.toString());
    }

    /**
     * Scan keywords or literals.
     */
    private void scanKeywordOrLiteral() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(input.current());

        while (input.hasNext()) {
            final char currentChar = input.next();

            if (!CharacterHelper.isAlphaNum(currentChar)) {
                break;
            }
            buffer.append(currentChar);
        }

        determineKeywordOrLiteralToken(buffer.toString());
    }

    /**
     * Determines if a literal is a keyword or not.
     *
     * @param literal literal to examine
     */
    private void determineKeywordOrLiteralToken(final String literal) {
        if (Keyword.isKeyword(literal)) {
            currentToken = Token.newKeywordToken(literal);
        } else {
            currentToken = Token.newLiteralToken(literal);
        }
    }

    /**
     * Scan string.
     */
    private void scanString() {
        final StringBuilder buffer = new StringBuilder();
//        input.next(); // consume "

        while (input.hasNext()) {
            if ('"' == input.next()) {
                if (input.hasNext()) {
                    input.next(); // consume "
                }
                currentToken = Token.newStringToken(buffer.toString());
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
        buffer.append(input.current());
        boolean isFloat = false;

        while (input.hasNext()) {
            final char currentChar = input.next();

            if ('.' == currentChar) {
                isFloat = true;
                buffer.append(currentChar);
                continue;
            }

            if (!CharacterHelper.isNum(currentChar)) {
                break;
            }
            buffer.append(currentChar);
        }

        if (isFloat) {
            currentToken = Token.newFloatToken(Float.valueOf(buffer.toString()));
        } else {
            currentToken = Token.newIntegerToken(Integer.valueOf(buffer.toString()));
        }
    }


}

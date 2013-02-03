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
class Scanner {

    private final CharacterStream input;
    private Token<?> currentToken = null;


    private Scanner(final CharacterStream input) {
        super();
        this.input = input;
    }

    static Scanner forString(final String string) {
        return new Scanner(new CharacterStream(string));
    }

    Token<?> getCurrentToken() {
        if (null == currentToken) {
            next();
        }

        return currentToken;
    }

    boolean hasNext() {
        return input.hasNext();
    }

    void next() {
        while (input.hasNext()) {
            final char currentCharacter = input.current();

            if (CharacterHelper.isWhiteSpace(currentCharacter)) {
                input.next(); // consume whitespace
                continue;
            }

            if (CharacterHelper.isAlpha(currentCharacter)) {
                scanKeywordOrIdentifier();
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

    private void scanOperator() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void scanKeywordOrIdentifier() {
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

    private void determineKeywordOrLiteralToken(final String literal) {
        if (Keyword.isKeyword(literal)) {
            currentToken = Token.newKeywordToken(literal);
        } else {
            currentToken = Token.newLiteralToken(literal);
        }
    }

    private void scanString() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void scanNumber() {
        throw new UnsupportedOperationException("Not yet implemented");
    }


}

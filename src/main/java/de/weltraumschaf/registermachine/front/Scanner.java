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
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
class Scanner {

    private final CharacterStream input;

    private int column;
    private int line;
    private Token<?> currentToken = null;


    private Scanner(final CharacterStream input) {
        super();
        this.input = input;
    }


    static Scanner forString(final String string) {
        return new Scanner(new CharacterStream(string));
    }

    Token getCurrentToken() {
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
                continue;
            }

            if (CharacterHelper.isAlpha(currentCharacter)) {
                scanKeywordOrIdentifier();
            }

            if (CharacterHelper.isNum(currentCharacter)) {
                scanNumber();
            }

            if (CharacterHelper.isDoubleQuote(currentCharacter)) {
                scanString();
            }

            if ('/' == currentCharacter) {
                scanCommentOrOperator();
                continue;
            }

            if (CharacterHelper.isOperator(currentCharacter)) {
                scanOperator();
                continue;
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
                input.next(); // consume new line
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
            if ('*' == currentCharacter && '/' == input.peek()) {
                input.next(); // consume star
                input.next(); // consume slash
                break;
            }
            buffer.append(currentCharacter);
        }

        currentToken = Token.newCommentToken(buffer.toString());
    }

    private void scanOperator() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void scanKeywordOrIdentifier() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void scanString() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void scanNumber() {
        throw new UnsupportedOperationException("Not yet implemented");
    }


}

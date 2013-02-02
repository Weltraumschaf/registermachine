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

import de.weltraumschaf.commons.characters.CharacterStream;
import de.weltraumschaf.commons.token.Token;

/**
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
class Scanner {

    private final CharacterStream input;

    private Scanner(final CharacterStream input) {
        super();
        this.input = input;
    }

    void next() {

    }

    boolean hasNext() {
        return false;
    }

    Token getCurrentToken() {
        return Token.newEndOfFileToken();
    }

    static Scanner forString(final String string) {
        return new Scanner(new CharacterStream(string));
    }

}

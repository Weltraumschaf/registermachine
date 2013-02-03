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

import de.weltraumschaf.registermachine.inter.AstNode;
import de.weltraumschaf.registermachine.inter.NopNode;

/**
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
final class Parser {

    private final Scanner scanner;

    private Parser(final Scanner scanner) {
        super();
        this.scanner = scanner;
    }

    static Parser forString(final String string) {
        return new Parser(Scanner.forString(string));
    }

    void parse() {

    }

    AstNode getAbstractSyntaxtTree() {
        return new NopNode();
    }

}

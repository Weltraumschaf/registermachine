/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */

package de.weltraumschaf.registermachine.asm;

import de.weltraumschaf.registermachine.typing.Function;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class LineParser {

    private final LineScanner scanner = new LineScanner();
    private Function mainfunction = null;
    private int currentLine;

    private void resetCurrentLine() {
        currentLine = 0;
    }

    private void incCurrentLine() {
        ++currentLine;
    }

    private int getCurrentLine() {
        return currentLine;
    }

    Function parseLines(final List<String> lines) throws AssemblerSyntaxException {
        resetCurrentLine();
        for (final String line : lines) {
            incCurrentLine();

            if (StringUtils.isBlank(line)) {
                continue;
            }

            final List<Token> tokens = scanner.parse(line);

            if (tokens.isEmpty()) {
                throw new AssemblerSyntaxException(String.format("Can't compile! No tokens found."), getCurrentLine());
            }

            switch (tokens.get(0).getType()) {
                case METACODE:
                    parseMetaCode(tokens);
                    break;
                case OPCODE:
                    parseOpCode(tokens);
                    break;
                default:
                    throw new AssemblerSyntaxException(String.format("First token of line neither mnemonic nor metacode!"), getCurrentLine());
            }
        }

        return mainfunction;
    }

    private void parseMetaCode(List<Token> tokens) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void parseOpCode(List<Token> tokens) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}

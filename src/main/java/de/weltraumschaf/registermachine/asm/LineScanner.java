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

import com.google.common.collect.Lists;
import de.weltraumschaf.registermachine.bytecode.OpCode;
import java.util.List;
import org.apache.commons.lang.StringUtils;


/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class LineScanner {

    List<Token> parse(final String line) throws AssemblerSyntaxException {
        return parse(new CharStream(line));
    }

    List<Token> parse(final CharStream line) throws AssemblerSyntaxException {
        final List<Token> tokens = Lists.newArrayList();

        if (line.isEmpty()) {
            return tokens;
        }

        do {
            char c = line.getCurrentChar();

            if (';' == c) {
                return tokens; // ignore rest of line
            } else if ('.' == c) {
                tokens.add(scanMeta(line));
//            } else if ('"' == c) {
//                scaenString(line);
//            } else if (StringUtils.isNumeric(String.valueOf(c))) {
//                scaenNumber(line);
            } else if (StringUtils.isAlpha(String.valueOf(c))) {
                tokens.add(scaenOpCodeOrLiteral(line));
            } else if (StringUtils.isWhitespace(String.valueOf(c))) {
                // ignore
            } else {
                throw new AssemblerSyntaxException(String.format("Unexpected character '%s' at column %d!",
                        String.valueOf(c), line.getIndex()), line.getLineNumber());
            }

            line.nextChar();
        } while (line.hasNextChar()) ;

        return tokens;
    }

    private Token scaenString(CharStream line) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Token scaenNumber(CharStream line) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Token scaenOpCodeOrLiteral(final CharStream line) {
        final StringBuilder value = new StringBuilder();
        value.append(line.getCurrentChar());
        TokenType type = null;

        while (line.hasNextChar()) {
            line.nextChar();
            final char c = line.getCurrentChar();

            if (StringUtils.isAlpha(String.valueOf(c))) {
                value.append(c);
            } else if (StringUtils.isNumeric(String.valueOf(c))) {
                value.append(c);
                type = TokenType.LITERAL; // Memonics can not contain numbers.
            } else {
                break;
            }
        }

        if (null == type) {
            if (OpCode.lokup(value.toString()) == OpCode.UNKWONN) {
                type = TokenType.LITERAL;
            } else {
                type = TokenType.OPCODE;
            }
        }

        return new Token(type, value.toString());
    }

    private Token scanMeta(final CharStream line) {
        final StringBuilder value = new StringBuilder();
        value.append(line.getCurrentChar());

        while (line.hasNextChar()) {
            line.nextChar();
            final char c = line.getCurrentChar();

            if (StringUtils.isAlpha(String.valueOf(c))) {
                value.append(c);
            } else {
                break;
            }
        }
        return new Token(TokenType.METACODE, value.toString());
    }

}

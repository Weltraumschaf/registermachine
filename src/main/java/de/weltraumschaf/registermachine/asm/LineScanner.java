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

/**
 * Scans a line of assembler codes into tokens.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class LineScanner {

    /**
     * Convenience method for handling strings.
     *
     * @param line line string
     * @return list of tokens, may be empty list, never null
     * @throws AssemblerSyntaxException if, a syntax error is found
     */
    List<Token> parse(final String line) throws AssemblerSyntaxException {
        return parse(new CharStream(line));
    }

    /**
     * Parses a given {@link CharStream} into tokens.
     *
     * @param line line in a character accessible form
     * @return list of tokens, may be empty list, never null
     * @throws AssemblerSyntaxException if, a syntax error is found
     */
    List<Token> parse(final CharStream line) throws AssemblerSyntaxException {
        final List<Token> tokens = Lists.newArrayList();

        if (line.isEmpty()) {
            return tokens;
        }

        while (line.hasNextChar()) {
            final char c = line.getCurrentChar();

            if (';' == c) {
                return tokens; // ignore rest of line
            } else if ('.' == c) {
                tokens.add(scanMeta(line));
                line.nextChar(); // consume last char
            } else if ('"' == c) {
                tokens.add(scaenString(line));
            } else if (CharUtils.isNumeric(c)) {
                tokens.add(scaenNumber(line));
                line.nextChar();
            } else if (CharUtils.isAlpha(c)) {
                tokens.add(scaenOpCodeOrLiteral(line));
                line.nextChar();
            } else if (CharUtils.isWhitespace(c)) { // NOPMD
                line.nextChar(); // consume and ignore whitespace
            } else {
                throw new AssemblerSyntaxException(String.format("Unexpected character '%s' at column %d!",
                        String.valueOf(c), line.getIndex()), line.getLineNumber());
            }
        }

        return tokens;
    }

    // TODO Fix the problem if " are insidethe string.
    private Token scaenString(final CharStream line) throws AssemblerSyntaxException {
        if ('"' != line.getCurrentChar()) {
            throw new AssemblerSyntaxException(
                    String.format("String must start with \" at column %d!", line.getIndex()),
                    line.getLineNumber());
        }
        final String errorMessage = "Unterminated string at column %d!";
        line.nextChar(); // consume "
        if (!line.hasNextChar()) {
            throw new AssemblerSyntaxException(String.format(errorMessage, line.getIndex()), line.getLineNumber());
        }

        final StringBuilder value = new StringBuilder();
        value.append(line.getCurrentChar());
        boolean terminationSeen = false;

        while (line.hasNextChar()) {
            line.nextChar();
            final char c = line.getCurrentChar();

            if ('"' == c) {
                terminationSeen = true;
                line.nextChar(); // consume "
                break;
            }

            value.append(c);
        }

        if (!terminationSeen) {
            throw new AssemblerSyntaxException(String.format(errorMessage, line.getIndex()), line.getLineNumber());
        }

        return new Token(TokenType.STRING, value.toString());
    }

    private Token scaenNumber(CharStream line) throws AssemblerSyntaxException {
        if (!CharUtils.isNumeric(line.getCurrentChar())) {
            throw new AssemblerSyntaxException(
                    String.format("Number must start with digit at index %d!", line.getIndex()),
                    line.getLineNumber());
        }
        final StringBuilder value = new StringBuilder();
        value.append(line.getCurrentChar());
        TokenType type = TokenType.INTEGER;

        while (line.hasNextChar()) {
            final char peek = line.peekChar();

            if (CharUtils.isNumeric(peek)) {
                line.nextChar();
                value.append(line.getCurrentChar());
            } else if ('.' == peek) {
                line.nextChar();
                value.append(line.getCurrentChar());
                type = TokenType.FLOAT;
            } else if (CharUtils.isWhitespace(peek) || peek == CharStream.EOL) {
                break;
            } else {
                throw new AssemblerSyntaxException(
                        String.format("Unexpected character %s at column %d!", peek, line.getIndex()),
                        line.getLineNumber());
            }
        }

        return new Token(type, value.toString());
    }

    private Token scaenOpCodeOrLiteral(final CharStream line) throws AssemblerSyntaxException {
        if (!CharUtils.isAlpha(line.getCurrentChar())) {
            throw new AssemblerSyntaxException(
                    String.format("Opcodes or literals must start with alpha character at column %d!", line.getIndex()),
                    line.getLineNumber());
        }
        final StringBuilder value = new StringBuilder();
        value.append(line.getCurrentChar());
        TokenType type = null;

        do {
            final char peek = line.peekChar();

            if (CharUtils.isAlpha(peek)) {
                line.nextChar();
                value.append(line.getCurrentChar());
            } else if (CharUtils.isNumeric(peek)) {
                line.nextChar();
                value.append(line.getCurrentChar());
                type = TokenType.LITERAL; // Memonics can not contain numbers.
            } else {
                break;
            }
        } while (line.hasNextChar());

        if (null == type) {
            if (OpCode.lokup(value.toString()) == OpCode.UNKWONN) {
                type = TokenType.LITERAL;
            } else {
                type = TokenType.OPCODE;
            }
        }

        return new Token(type, value.toString());
    }

    private Token scanMeta(final CharStream line) throws AssemblerSyntaxException {
        if (line.getCurrentChar() != '.') {
            throw new AssemblerSyntaxException(
                    String.format("Meta mnemonic at index %d must start with '.'!", line.getIndex()),
                    line.getLineNumber());
        }

        final StringBuilder value = new StringBuilder();
        value.append(line.getCurrentChar());

        if (!line.hasNextChar() || !CharUtils.isAlpha(line.peekChar())) {
            throw new AssemblerSyntaxException(
                    String.format("Too short meta code mnemonic at column %d!", line.getIndex()),
                    line.getLineNumber());
        }

        do {
            line.nextChar();
            value.append(line.getCurrentChar());
        } while (line.hasNextChar() && CharUtils.isAlpha(line.peekChar()));

        return new Token(TokenType.METACODE, value.toString());
    }
}

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
import de.weltraumschaf.registermachine.ByteArray;
import de.weltraumschaf.registermachine.bytecode.ByteCode;
import de.weltraumschaf.registermachine.bytecode.ByteCodeFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Assambler {

    public ByteCodeFile assamble(final InputStream input) throws IOException, AssamblerSyntaxException {
        final List<String> lines = IOUtils.readLines(input);
        IOUtils.closeQuietly(input);
        List<Byte> bytecode = Lists.newArrayList();
        bytecode.add(Byte.valueOf((byte) 0xCA));
        bytecode.add(Byte.valueOf((byte) 0x7E));
        bytecode.add(Byte.valueOf((byte) 0x01));
        processLines(bytecode, lines);
        return new ByteCodeFile(convertToNativeArray(bytecode));
    }

    private byte[] convertToNativeArray(List<Byte> bytecode) {
        return ByteArray.toNative(bytecode.toArray(new Byte[bytecode.size()]));
    }

    private void processLines(final List<Byte> bytecode, final List<String> lines) throws AssamblerSyntaxException {
        for (final String line : lines) {
            processLines(bytecode, line);
        }
    }

    private void processLines(final List<Byte> bytecode, final String line) throws AssamblerSyntaxException {
        final String trimmedLine = StringUtils.trimToEmpty(line);

        if (trimmedLine.isEmpty()) {
            return;
        }

        if (trimmedLine.charAt(0) == ';') {
            return; // Ignore comment lines
        }

        final String pureCodeLine = trimComment(trimmedLine);
        final List<String> parts = Arrays.asList(StringUtils.split(pureCodeLine));
        processLineParts(bytecode, parts);
    }

    private String trimComment(final String line) {
        final int commentStart = line.indexOf(';');

        if (-1 == commentStart) {
            return line;
        }

        return StringUtils.trimToEmpty(line.substring(0, commentStart));
    }

    private void processLineParts(final List<Byte> bytecode, final List<String> parts) throws AssamblerSyntaxException {
        final List<String> args = parts.subList(1, parts.size());
        final ByteCode bc = ByteCode.lokup(parts.get(0));

        switch (bc) {
            case MOVE:
                generateMoveCode(bc, bytecode, args);
                break;
            case ADD:
                generateAddCode(bc, bytecode, args);
                break;
            case SUB:
                generateSubCode(bc, bytecode, args);
                break;
            case MUL:
                generateMullCode(bc, bytecode, args);
                break;
            case DIV:
                generateDivCode(bc, bytecode, args);
                break;
            case UNKWONN:
            default:
                throw new AssamblerSyntaxException(String.format("Unknown mnemonic '%s'!", parts.get(0)));

        }
    }

    private void generateMoveCode(final ByteCode bc, final List<Byte> bytecode, final List<String> args) throws AssamblerSyntaxException {
        if (args.size() != 2) {
            throw new AssamblerSyntaxException("move requires two arguments!");
        }

        bytecode.add(bc.getCode());

        if (!StringUtils.isNumeric(args.get(0))) {
            throw new AssamblerSyntaxException("argument one not numeric!");
        }

        bytecode.add(Byte.valueOf(args.get(0)));

        if (!StringUtils.isNumeric(args.get(1))) {
            throw new AssamblerSyntaxException("argument two not numeric!");
        }

        bytecode.add(Byte.valueOf(args.get(1)));
    }

    private void generateAddCode(final ByteCode bc, final List<Byte> bytecode, final List<String> args) throws AssamblerSyntaxException {
        if (args.size() != 3) {
            throw new AssamblerSyntaxException("add requires three arguments!");
        }

        bytecode.add(bc.getCode());

        if (!StringUtils.isNumeric(args.get(0))) {
            throw new AssamblerSyntaxException("argument one not numeric!");
        }

        bytecode.add(Byte.valueOf(args.get(0)));

        if (!StringUtils.isNumeric(args.get(1))) {
            throw new AssamblerSyntaxException("argument two not numeric!");
        }

        bytecode.add(Byte.valueOf(args.get(1)));

        if (!StringUtils.isNumeric(args.get(2))) {
            throw new AssamblerSyntaxException("argument three not numeric!");
        }

        bytecode.add(Byte.valueOf(args.get(2)));
    }

    private void generateSubCode(final ByteCode bc, final List<Byte> bytecode, final List<String> args) throws AssamblerSyntaxException {
        if (args.size() != 3) {
            throw new AssamblerSyntaxException("sub requires three arguments!");
        }

        bytecode.add(bc.getCode());

        if (!StringUtils.isNumeric(args.get(0))) {
            throw new AssamblerSyntaxException("argument one not numeric!");
        }

        bytecode.add(Byte.valueOf(args.get(0)));

        if (!StringUtils.isNumeric(args.get(1))) {
            throw new AssamblerSyntaxException("argument two not numeric!");
        }

        bytecode.add(Byte.valueOf(args.get(1)));

        if (!StringUtils.isNumeric(args.get(2))) {
            throw new AssamblerSyntaxException("argument three not numeric!");
        }

        bytecode.add(Byte.valueOf(args.get(2)));
    }

    private void generateMullCode(final ByteCode bc, final List<Byte> bytecode, final List<String> args) throws AssamblerSyntaxException {
        if (args.size() != 3) {
            throw new AssamblerSyntaxException("mul requires three arguments!");
        }

        bytecode.add(bc.getCode());

        if (!StringUtils.isNumeric(args.get(0))) {
            throw new AssamblerSyntaxException("argument one not numeric!");
        }

        bytecode.add(Byte.valueOf(args.get(0)));

        if (!StringUtils.isNumeric(args.get(1))) {
            throw new AssamblerSyntaxException("argument two not numeric!");
        }

        bytecode.add(Byte.valueOf(args.get(1)));

        if (!StringUtils.isNumeric(args.get(2))) {
            throw new AssamblerSyntaxException("argument three not numeric!");
        }

        bytecode.add(Byte.valueOf(args.get(2)));
    }

    private void generateDivCode(final ByteCode bc, final List<Byte> bytecode, final List<String> args) throws AssamblerSyntaxException {
        if (args.size() != 3) {
            throw new AssamblerSyntaxException("div requires three arguments!");
        }

        bytecode.add(bc.getCode());

        if (!StringUtils.isNumeric(args.get(0))) {
            throw new AssamblerSyntaxException("argument one not numeric!");
        }

        bytecode.add(Byte.valueOf(args.get(0)));

        if (!StringUtils.isNumeric(args.get(1))) {
            throw new AssamblerSyntaxException("argument two not numeric!");
        }

        bytecode.add(Byte.valueOf(args.get(1)));

        if (!StringUtils.isNumeric(args.get(2))) {
            throw new AssamblerSyntaxException("argument three not numeric!");
        }

        bytecode.add(Byte.valueOf(args.get(2)));
    }

}

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
import de.weltraumschaf.registermachine.App;
import de.weltraumschaf.registermachine.convert.ByteArray;
import de.weltraumschaf.registermachine.convert.ByteInteger;
import de.weltraumschaf.registermachine.bytecode.ByteCodeFile;
import de.weltraumschaf.registermachine.bytecode.ByteCodeStream;
import de.weltraumschaf.registermachine.bytecode.OpCode;
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
public class Assembler {

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

    public ByteCodeFile assamble(final InputStream input) throws IOException, AssemblerSyntaxException {
        final List<String> lines = IOUtils.readLines(input, App.ENCODING);
        IOUtils.closeQuietly(input);
        final List<Byte> bytecode = createByteCodeHeader();
        processLines(bytecode, lines);
        return new ByteCodeFile(ByteArray.convertToNativeArray(bytecode));
    }

    private List<Byte> createByteCodeHeader() {
        final List<Byte> bytecode = Lists.newArrayList();
        bytecode.add(Byte.valueOf(ByteCodeStream.BC_FST_HEADER_BYTE));
        bytecode.add(Byte.valueOf(ByteCodeStream.BC_SND_HEADER_BYTE));
        final byte[] version = ByteInteger.bytesFromInt(ByteCodeStream.BC_CURRENT_VERSION);
        bytecode.add(Byte.valueOf(version[0]));
        bytecode.add(Byte.valueOf(version[1]));
        return bytecode;
    }

    private void processLines(final List<Byte> bytecode, final List<String> lines) throws AssemblerSyntaxException {
        resetCurrentLine();
        for (final String line : lines) {
            incCurrentLine();
            processLines(bytecode, line);
        }
    }

    private void processLines(final List<Byte> bytecode, final String line) throws AssemblerSyntaxException {
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

    private void processLineParts(final List<Byte> bytecode, final List<String> parts) throws AssemblerSyntaxException {
        final List<String> args = parts.subList(1, parts.size());
        final OpCode bc = OpCode.lokup(parts.get(0));

        if (bc == OpCode.UNKWONN) {
            throw new AssemblerSyntaxException(String.format("Unknown mnemonic '%s'!", parts.get(0)), getCurrentLine());
        } else {
            assertArgCount(args, bc);
            bytecode.add(bc.getCode());

            for (int i = 0; i < args.size(); ++i) {
                bytecode.addAll(createByteListFromNumericArg(args.get(i)));
            }
        }
    }

    private List<Byte> createByteListFromNumericArg(final String arg) throws AssemblerSyntaxException {
        if (!StringUtils.isNumeric(arg)) {
            throw new AssemblerSyntaxException("Argument one not numeric!", getCurrentLine());
        }

        final Integer value = Integer.valueOf(arg);
        final byte[] bytes = ByteInteger.bytesFromInt(value);
        return Arrays.asList(ByteArray.toObject(bytes));
    }

    private void assertArgCount(final List<String> args, final OpCode bc) throws AssemblerSyntaxException {
        if (args.size() != bc.getArgCount().getCount()) {
            throw new AssemblerSyntaxException(String.format("Opcode %s requires %d arguments!",
                                                             bc.name(),
                                                             bc.getArgCount().getCount()),
                    getCurrentLine());
        }
    }

}

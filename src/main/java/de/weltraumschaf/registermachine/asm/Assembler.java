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
import de.weltraumschaf.registermachine.ByteInt;
import de.weltraumschaf.registermachine.Const;
import de.weltraumschaf.registermachine.bytecode.ByteCodeFile;
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

    public ByteCodeFile assamble(final InputStream input) throws IOException, AssemblerSyntaxException {
        final List<String> lines = IOUtils.readLines(input, Const.ENCODING);
        IOUtils.closeQuietly(input);
        final List<Byte> bytecode = createByteCodeHeader();
        processLines(bytecode, lines);
        return new ByteCodeFile(convertToNativeArray(bytecode));
    }

    private List<Byte> createByteCodeHeader() {
        final List<Byte> bytecode = Lists.newArrayList();
        bytecode.add(Byte.valueOf(Const.BC_FST_HEADER_BYTE));
        bytecode.add(Byte.valueOf(Const.BC_SND_HEADER_BYTE));
        final byte[] version = ByteInt.bytesFromInt(Const.BC_CURRENT_VERSION);
        bytecode.add(Byte.valueOf(version[0]));
        bytecode.add(Byte.valueOf(version[1]));
        return bytecode;
    }
    private byte[] convertToNativeArray(List<Byte> bytecode) {
        return ByteArray.toNative(bytecode.toArray(new Byte[bytecode.size()]));
    }

    private void processLines(final List<Byte> bytecode, final List<String> lines) throws AssemblerSyntaxException {
        for (final String line : lines) {
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
            throw new AssemblerSyntaxException(String.format("Unknown mnemonic '%s'!", parts.get(0)));
        } else {
            assertArgCount(args, bc);
            bytecode.add(bc.getCode());

            for (int i = 0; i < args.size(); ++i) {
                bytecode.addAll(createByteListFromNumericArg(args.get(i)));
            }
        }
    }

    private static final String ARG_CNT_ERR_FMT = "Opcode %s requires %d arguments!";

    private List<Byte> createByteListFromNumericArg(final String arg) throws AssemblerSyntaxException {
        if (!StringUtils.isNumeric(arg)) {
            throw new AssemblerSyntaxException("argument one not numeric!");
        }

        final Integer value = Integer.valueOf(arg);
        final byte[] bytes = ByteInt.bytesFromInt(value);
        return Arrays.asList(ByteArray.toObject(bytes));
    }

    private void assertArgCount(final List<String> args, final OpCode bc) throws AssemblerSyntaxException {
        if (args.size() != bc.getArgCount().getCount()) {
            throw new AssemblerSyntaxException(String.format(ARG_CNT_ERR_FMT, bc.name(), bc.getArgCount().getCount()));
        }
    }

}

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
import com.google.common.primitives.Bytes;
import de.weltraumschaf.registermachine.App;
import de.weltraumschaf.registermachine.bytecode.ByteCodeFile;
import de.weltraumschaf.registermachine.bytecode.ByteCodeStream;
import de.weltraumschaf.registermachine.convert.ByteInteger;
import de.weltraumschaf.registermachine.convert.ByteString;
import de.weltraumschaf.registermachine.typing.Function;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Assembler {

    private final LineParser parser = new LineParser();

    public ByteCodeFile assamble(final InputStream input, final String sourceFileName)
        throws IOException, AssemblerSyntaxException {
        final List<String> lines = IOUtils.readLines(input, App.ENCODING);
        IOUtils.closeQuietly(input);
        final List<Byte> bytecode = createByteCodeWithHeader(sourceFileName);
        final Function main = parser.parseLines(lines);
        bytecode.addAll(main.asByteList());
        return new ByteCodeFile(bytecode);
    }

    private List<Byte> createByteCodeWithHeader(final String sourceFileName) throws UnsupportedEncodingException {
        final List<Byte> bytecode = Lists.newArrayList();
        bytecode.add(Byte.valueOf(ByteCodeStream.BC_FST_HEADER_BYTE));
        bytecode.add(Byte.valueOf(ByteCodeStream.BC_SND_HEADER_BYTE));
        final byte[] version = ByteInteger.bytesFromShort(ByteCodeStream.BC_CURRENT_VERSION);
        bytecode.addAll(Bytes.asList(version));
        final byte[] filename = ByteString.bytesFromString(sourceFileName);
        final byte[] filenameLength = ByteInteger.bytesFromInt(filename.length);
        bytecode.addAll(Bytes.asList(filenameLength));
        bytecode.addAll(Bytes.asList(filename));
        return bytecode;
    }
}

/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" weltraumschaf@googlemail.com wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" weltraumschaf@googlemail.com
 */
package de.weltraumschaf.registermachine.asm;

import de.weltraumschaf.registermachine.Foo;
import de.weltraumschaf.registermachine.bytecode.ByteCodeFile;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Sven Strittmatter weltraumschaf@googlemail.com
 */
public class AssemblerTest {

    private static final String PACKAGE_PREFIX = "/de/weltraumschaf/registermachine/asm";
    private final Assembler sut = new Assembler();

    private ByteCodeFile createAndVerifyByteCodeFile(final String filename) throws IOException, AssemblerSyntaxException {
        final InputStream input = getClass().getResourceAsStream(PACKAGE_PREFIX + "/" + filename);
        final ByteCodeFile byteCodeFile = sut.assamble(input, filename);
        input.close();
        assertThat(byteCodeFile.isValid(), is(true));
        assertThat(byteCodeFile.getVersion(), is((short) 0x02));
        return byteCodeFile;
    }

    @Test
    public void assamble_emptyMainFunction() throws IOException, AssemblerSyntaxException {
        final byte[] bytecode = createAndVerifyByteCodeFile("empty_main_function.ctasm").toArray();
        assertThat(bytecode.length, is(41));
        assertThat(bytecode, is(new byte[]{
                    (byte) 0xca, 0x7e, // singature
                    0x02, 0x00, // version
                    0x19, 0x00, 0x00, 0x00, // source name length
                    0x65, 0x6d, 0x70, 0x74, 0x79, 0x5f, 0x6d, 0x61, // source name
                    0x69, 0x6e, 0x5f, 0x66, 0x75, 0x6e, 0x63, 0x74, // source name
                    0x69, 0x6f, 0x6e, 0x2e, 0x63, 0x74, 0x61, 0x73, // source name
                    0x6d, // source name
                    // function [0] definition (level 0)
                    0x00, // nups
                    0x00, // numparams
                    0x04, // isVararg
                    0x02, // maxStackSize
                    0x00, // sizeCode
                    0x00, // sizeConstants
                    0x00, // sizeVariables
                    0x00, // sizeFunctions
                }));
    }

    @Test
    public void assamble_mainFunctionWithCode() throws IOException, AssemblerSyntaxException {
        final byte[] bytecode = createAndVerifyByteCodeFile("main_function_with_code.ctasm").toArray();
        assertThat(bytecode.length, is(95));
        assertThat(bytecode, is(new byte[]{
                    (byte) 0xca, 0x7e, // singature
                    0x02, 0x00, // version
                    0x1d, 0x00, 0x00, 0x00, // source name length
                    0x6d, 0x61, 0x69, 0x6e, 0x5f, 0x66, 0x75, 0x6e, // source name
                    0x63, 0x74, 0x69, 0x6f, 0x6e, 0x5f, 0x77, 0x69, // source name
                    0x74, 0x68, 0x5f, 0x63, 0x6f, 0x64, 0x65, 0x2e, // source name
                    0x63, 0x74, 0x61, 0x73, 0x6d, // source name
                    // function [0] definition (level 0)
                    0x01, // nups
                    0x02, // numparams
                    0x03, // isVararg
                    0x04, // maxStackSize
                    0x06, // sizeCode
                    0x02, 0x04, 0x00, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, // loadc 4 4
                    0x11, 0x01, 0x00, 0x00, 0x00, // println 1
                    0x03, 0x01, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, // add 1 2
                    0x01, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, // move 0 1
                    0x0d, 0x01, 0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, // lt 1 3
                    0x0f, 0x00, 0x00, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, // test 0 4
                    0x00, // sizeConstants
                    0x00, // sizeVariables
                    0x00, // sizeFunctions
                }));
    }

    @Test
    public void assamble_mainFunctionWithVariables() throws IOException, AssemblerSyntaxException {
        final byte[] bytecode = createAndVerifyByteCodeFile("main_function_with_variables.ctasm").toArray();
        assertThat(bytecode.length, is(65));
        assertThat(bytecode, is(new byte[]{
                    (byte) 0xca, 0x7e, // singature
                    0x02, 0x00, // version
                    0x22, 0x00, 0x00, 0x00, // source name length
                    0x6d, 0x61, 0x69, 0x6e, 0x5f, 0x66, 0x75, 0x6e, // source name
                    0x63, 0x74, 0x69, 0x6f, 0x6e, 0x5f, 0x77, 0x69, // source name
                    0x74, 0x68, 0x5f, 0x76, 0x61, 0x72, 0x69, 0x61, // source name
                    0x62, 0x6c, 0x65, 0x73, 0x2e, 0x63, 0x74, 0x61, // source name
                    0x73, 0x6d, // source name
                    // function [0] definition (level 0)
                    0x01, // nups
                    0x02, // numparams
                    0x03, // isVararg
                    0x04, // maxStackSize
                    0x00, // sizeCode
                    0x00, // sizeConstants
                    0x03, // sizeVariables
                    0x04, 0x03, 0x66, 0x6f, 0x6f, // String "foo"
                    0x01, 0x2a, 0x00, 0x00, 0x00, // Integer 42
                    0x02, 0x33, 0x33, 0x13, 0x40, // Float 2.3
                    0x00, // sizeFunctions
                }));
    }

    @Test @Ignore
    public void assamble_mainFunctionWithConstants() throws IOException, AssemblerSyntaxException {
        final byte[] bytecode = createAndVerifyByteCodeFile("main_function_with_constants.ctasm").toArray();
        assertThat(bytecode.length, is(95));
        assertThat(bytecode, is(new byte[]{
                    (byte) 0xca, 0x7e, // singature
                    0x02, 0x00, // version
                    0x1d, 0x00, 0x00, 0x00, // source name length
                    }));
    }

    @Test @Ignore
    public void assamble_mainFunctionWithFunctions() throws IOException, AssemblerSyntaxException {
        final byte[] bytecode = createAndVerifyByteCodeFile("main_function_with_functions.ctasm").toArray();
        assertThat(bytecode.length, is(95));
        assertThat(bytecode, is(new byte[]{
                    (byte) 0xca, 0x7e, // singature
                    0x02, 0x00, // version
                    0x1d, 0x00, 0x00, 0x00, // source name length
                    }));
    }

    @Test @Ignore
    public void assamble_mainFunctionWithCodeVariablesConstantsAndFunctions() throws IOException, AssemblerSyntaxException {
        final byte[] bytecode = createAndVerifyByteCodeFile("main_function_with_code_vars_constants_and_functions.ctasm").toArray();
        assertThat(bytecode.length, is(95));
        assertThat(bytecode, is(new byte[]{
                    (byte) 0xca, 0x7e, // singature
                    0x02, 0x00, // version
                    0x1d, 0x00, 0x00, 0x00, // source name length
                    }));
    }


}

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

package de.weltraumschaf.registermachine;

import de.weltraumschaf.commons.CapturingOutputStream;
import de.weltraumschaf.commons.IO;
import de.weltraumschaf.commons.IOStreams;
import de.weltraumschaf.registermachine.asm.Assembler;
import de.weltraumschaf.registermachine.asm.AssemblerSyntaxException;
import de.weltraumschaf.registermachine.bytecode.ByteCodeFile;
import de.weltraumschaf.registermachine.vm.Executor;
import de.weltraumschaf.registermachine.vm.RegisterMachine;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@SystemTest
public class CompileAndRunTest {

    private static final String PACKAGE_PREFIX = "/de/weltraumschaf/registermachine/vm";
    private final CapturingOutputStream out = new CapturingOutputStream();
    private final IO io = new IOStreams(mock(InputStream.class), new PrintStream(out), mock(PrintStream.class));

    private InputStream createFromPackageResource(final String resourcePath) throws IOException {
        return getClass().getResourceAsStream(PACKAGE_PREFIX + resourcePath);
    }

    @Ignore
    @Test public void loopFiveTimesAndPrint() throws IOException, AssemblerSyntaxException {
        // TODO fix this
        final Assembler asm = new Assembler();
        final String filename = "loopFiveTimesAndPrint.ctasm";
        final ByteCodeFile bc = asm.assamble(createFromPackageResource("/" + filename), filename);
        assertThat(bc.isValid(), is(true));
        final Executor exec = new Executor(new RegisterMachine(io), io);
        exec.execute(bc);
        assertThat(out.getCapturedOutput(), is("0" + App.NL
                + "1" + App.NL
                + "2" + App.NL
                + "3" + App.NL
                + "4" + App.NL
                + "HALT." + App.NL + App.NL));
    }

}

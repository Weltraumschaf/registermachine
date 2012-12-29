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
package de.weltraumschaf.registermachine;

import de.weltraumschaf.commons.IOStreams;
import de.weltraumschaf.commons.InvokableAdapter;
import de.weltraumschaf.commons.Version;
import de.weltraumschaf.registermachine.asm.Assembler;
import de.weltraumschaf.registermachine.asm.AssemblerSyntaxException;
import de.weltraumschaf.registermachine.asm.Disassembler;
import de.weltraumschaf.registermachine.bytecode.ByteCodeFile;
import de.weltraumschaf.registermachine.bytecode.ByteCodeWriter;
import de.weltraumschaf.registermachine.bytecode.OpCode;
import de.weltraumschaf.registermachine.vm.Executor;
import de.weltraumschaf.registermachine.vm.RegisterMachine;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class App extends InvokableAdapter {

    private static final String EXECUTABLE = "machine";
    private static final String HEADER_FMT = "%n%nRegister based machine v%s.%n%n";
    private static final String AUTHOR = "Sven Strittmatter <weltraumschaf@googlemail.com>";
    /**
     * URI to issue tracker.
     */
    private static final String ISSUE_TRACKER = "https://github.com/Weltraumschaf/registermachine/issues";
    /**
     * Usage footer.
     */
    private static final String FOOTER = String.format("%nWritten 2012 by %s%nWrite bugs to %s%n",
            AUTHOR, ISSUE_TRACKER);
    private static final Options OPTIONS = new Options();
    private static final String CTASM_EXT = ".ctasm";
    private static final String BC_EXT = ".ct";
    private static final String HELP_OPT = "h";
    private static final String VERBOSE_OPT = "v";
    private static final String COMPILE_OPT = "c";
    private static final String PRINT_OPT = "p";
    private static final String EXECUTE_OPT = "e";
    private static final String DISASSEMBLE_OPT = "d";
    private static final String PRINT_OPCODES_OPT = "o";
    private static final String INTERPRET_OPT = "i";

    static {
        OPTIONS.addOption(HELP_OPT, false, "display help");
        OPTIONS.addOption(VERBOSE_OPT, false, "be more verbose");
        OPTIONS.addOption(COMPILE_OPT, true, "compile assembly source");
        OPTIONS.addOption(PRINT_OPT, false, "print program output");
        OPTIONS.addOption(EXECUTE_OPT, true, "executes byte code file");
        OPTIONS.addOption(DISASSEMBLE_OPT, true, "disassemble byte code file");
        OPTIONS.addOption(PRINT_OPCODES_OPT, false, "print list of al opcodes");
        OPTIONS.addOption(INTERPRET_OPT, true, "interpret assembly source");
    }

    private static final CommandLineParser PARSER = new PosixParser();
    private final CommandLine commandLineArgs;

    /**
     * Hide constructor because it is not intended to create objects of this type outside of
     * {@link App#main(de.weltraumschaf.commons.Invokable)}.
     *
     * @param args command line arguments
     */
    private App(final String[] args) {
        super(args);

        try {
            commandLineArgs = PARSER.parse(OPTIONS, getArgs());
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean isHelp() {
        return commandLineArgs.hasOption(HELP_OPT);
    }

    private boolean isVerbose() {
        return commandLineArgs.hasOption(VERBOSE_OPT);
    }

    private boolean isCompile() {
        return commandLineArgs.hasOption(COMPILE_OPT);
    }

    private String getCompile() {
        return commandLineArgs.getOptionValue(COMPILE_OPT);
    }

    private boolean isPrintProgram() {
        return commandLineArgs.hasOption(PRINT_OPT);
    }

    private boolean isExecute() {
        return commandLineArgs.hasOption(EXECUTE_OPT);
    }

    private String getExecute() {
        return commandLineArgs.getOptionValue(EXECUTE_OPT);
    }

    private boolean isDisassemble() {
        return commandLineArgs.hasOption(DISASSEMBLE_OPT);
    }

    private String getDisassemble() {
        return commandLineArgs.getOptionValue(DISASSEMBLE_OPT);
    }

    private boolean isPrintOpCodes() {
        return commandLineArgs.hasOption(PRINT_OPCODES_OPT);
    }

    private boolean isInterpret() {
        return commandLineArgs.hasOption(INTERPRET_OPT);
    }

    private String getInterpret() {
        return commandLineArgs.getOptionValue(INTERPRET_OPT);
    }

    /**
     * Main entry point for JVM.
     *
     * Catches all {@link Exception exception} and print their message to STDERR and exits with -1.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        final App app = new App(args);

        try {
            InvokableAdapter.main(app, IOStreams.newDefault(), app.isVerbose());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            app.exit(-1);
        }
    }

    /**
     * Starts interactive shell.
     *
     * @throws Exception if, something bad happens at runtime.
     */
    @Override
    public void execute() throws Exception {
        if (isHelp()) {
            showHelp();
        } else if (isCompile()) {
            assembleCode(getCompile());
        } else if (isDisassemble()) {
            disassembleCode(getDisassemble());
        } else if (isExecute()) {
            executeByteCode(getExecute());
        } else if (isPrintOpCodes()) {
            printOpCodes();
        } else if (isInterpret()) {
            interpretAssemblv(getInterpret());
        } else {
            getIoStreams().errorln("Bad arguments!");
            showHelp();
        }
    }

    private void disassembleCode(final String filename) throws FileNotFoundException, IOException {
        final Disassembler disasm = new Disassembler();
        final String asm = disasm.disassamble(FileIo.newInputStream(filename));
        getIoStreams().println(asm);
    }

    private void executeByteCode(final String filename) throws IOException {
        executeByteCode(new ByteCodeFile(FileIo.newInputStream(filename)));
    }

    private static String generateCompiledFileName(final String inFilename)  {
        return inFilename.replace(CTASM_EXT, "") + BC_EXT;
    }

    private void assembleCode(final String inFilename) throws IOException, AssemblerSyntaxException {
        getIoStreams().println(String.format("Compiling assembly file '%s' ...", inFilename));
        final String outFilename = generateCompiledFileName(inFilename);
        final ByteCodeWriter out = new ByteCodeWriter(FileIo.newOutputStream(outFilename));
        out.write(assembleFile(inFilename));
        getIoStreams().println(String.format("Saved assembled byte code to '%s'.", outFilename));
    }

    private void showHelp() throws IOException {
        final PrintWriter writer = new PrintWriter(getIoStreams().getStdout());
        final HelpFormatter formatter = new HelpFormatter();
        final Version version = new Version("/de/weltraumschaf/registermachine/version.properties");
        version.load();

        formatter.printHelp(
                writer,
                HelpFormatter.DEFAULT_WIDTH,
                EXECUTABLE,
                String.format(HEADER_FMT, version.toString()),
                OPTIONS,
                HelpFormatter.DEFAULT_LEFT_PAD,
                HelpFormatter.DEFAULT_DESC_PAD,
                FOOTER,
                true);
        writer.flush();
    }

    private void printOpCodes() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("mnemonic args   byte")
              .append(Const.NL)
              .append("--------------------")
              .append(Const.NL);
        final String fmt = "%s0x%s%n";
        for (final OpCode op : OpCode.values()) {
            final StringBuilder mnemonic = new StringBuilder();
            mnemonic.append(StringUtils.rightPad(op.name().toLowerCase(Const.LOCALE), 8))
                    .append(' ');
            char c = 'A';
            for (int i = 0; i < op.getArgCount().getCount(); ++i) {
                mnemonic.append(c).append(' ');
                ++c;
            }
            buffer.append(String.format(fmt, StringUtils.rightPad(mnemonic.toString(), 16), op.toHex()));
        }
        getIoStreams().print(buffer.toString());
    }

    private void interpretAssemblv(String inFilename) throws IOException, AssemblerSyntaxException {
        ByteCodeFile bc = assembleFile(inFilename);
        executeByteCode(bc);
    }

    private void executeByteCode(final ByteCodeFile bc) throws RuntimeException {
        if (!bc.isValid()) {
            throw new RuntimeException("Is not valid byte code!");
        }

        getIoStreams().println(String.format("Executing byte code version %d ...", bc.getVersion()));
        final RegisterMachine vm = new RegisterMachine(getIoStreams(), isVerbose(), isPrintProgram());
        final Executor exec = new Executor(vm, getIoStreams());
        exec.execute(bc);
    }

    private ByteCodeFile assembleFile(final String inFilename) throws AssemblerSyntaxException, IOException {
        final Assembler asm = new Assembler();
        return asm.assamble(FileIo.newInputStream(inFilename));
    }

}

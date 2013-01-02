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
import de.weltraumschaf.registermachine.vm.ExecutionException;
import de.weltraumschaf.registermachine.vm.Executor;
import de.weltraumschaf.registermachine.vm.RegisterMachine;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
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
 * Main application invoked by JVM.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class App extends InvokableAdapter {

    /**
     * Encoding used for all file I/O.
     */
    public static final String ENCODING = "UTF-8";
    /**
     * Locale used for all strings.
     */
    public static final Locale LOCALE = Locale.ENGLISH;
    /**
     * System dependent new line character.
     */
    public static final String NL = String.format("%n");

    /**
     * Name of the executable displayed in usage.
     */
    private static final String EXECUTABLE = "machine";
    /**
     * Help message header.
     */
    private static final String HEADER_FMT = "%n%nRegister based machine v%s.%n%n";
    /**
     * Author for help message footer.
     */
    private static final String AUTHOR = "Sven Strittmatter <weltraumschaf@googlemail.com>";
    /**
     * URI to issue tracker.
     */
    private static final String ISSUE_TRACKER = "https://github.com/Weltraumschaf/registermachine/issues";
    /**
     * Help footer.
     */
    private static final String FOOTER = String.format("%nWritten 2012 by %s%nWrite bugs to %s%n",
            AUTHOR, ISSUE_TRACKER);
    /**
     * File extension for assembly files.
     */
    private static final String CTASM_EXT = ".ctasm";
    /**
     * File extension for byte code files.
     */
    private static final String BC_EXT = ".ct";
    /**
     * Help option string.
     */
    private static final String HELP_OPT = "h";
    /**
     * Verbose option string.
     */
    private static final String VERBOSE_OPT = "v";
    /**
     * Compile option string.
     */
    private static final String COMPILE_OPT = "c";
    /**
     * Print program option string.
     */
    private static final String PRINT_PROGRAM_OPT = "p";
    /**
     * Execute option string.
     */
    private static final String EXECUTE_OPT = "e";
    /**
     * Disassemble option string.
     */
    private static final String DISASSEMBLE_OPT = "d";
    /**
     * Print opcode list option string.
     */
    private static final String PRINT_OPCODES_OPT = "o";
    /**
     * Interpret option string.
     */
    private static final String INTERPRET_OPT = "i";
    /**
     * Command line options.
     */
    private static final Options OPTIONS = new Options();
    /**
     * Pads the opcode mnemonic string for output.
     */
    private static final int MNEMONIC_PAD = 8;
    /**
     * PAds the mnemonic with argument place holders for output.
     */
    private static final int OPCODE_PAD = 16;

    static {
        OPTIONS.addOption(HELP_OPT, false, "display help");
        OPTIONS.addOption(VERBOSE_OPT, false, "be more verbose");
        OPTIONS.addOption(COMPILE_OPT, true, "compile assembly source");
        OPTIONS.addOption(PRINT_PROGRAM_OPT, false, "print program output");
        OPTIONS.addOption(EXECUTE_OPT, true, "executes byte code file");
        OPTIONS.addOption(DISASSEMBLE_OPT, true, "disassemble byte code file");
        OPTIONS.addOption(PRINT_OPCODES_OPT, false, "print list of al opcodes");
        OPTIONS.addOption(INTERPRET_OPT, true, "interpret assembly source");
    }
    /**
     * Command line argument parser.
     */
    private static final CommandLineParser PARSER = new PosixParser();
    /**
     * Command line arguments.
     */
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

    /**
     * Is help option flag present?
     *
     * @return true id -h present in CLI args, else false
     */
    private boolean isHelp() {
        return commandLineArgs.hasOption(HELP_OPT);
    }

    /**
     * Is verbose option flag present?
     *
     * @return true id -v present in CLI args, else false
     */
    private boolean isVerbose() {
        return commandLineArgs.hasOption(VERBOSE_OPT);
    }

    /**
     * Is compile option flag present?
     *
     * @return true id -c present in CLI args, else false
     */
    private boolean isCompile() {
        return commandLineArgs.hasOption(COMPILE_OPT);
    }

    private String getCompile() {
        return commandLineArgs.getOptionValue(COMPILE_OPT);
    }

    /**
     * Is print program option flag present?
     *
     * @return true id -p present in CLI args, else false
     */
    private boolean isPrintProgram() {
        return commandLineArgs.hasOption(PRINT_PROGRAM_OPT);
    }

    /**
     * Is execute option flag present?
     *
     * @return true id -e present in CLI args, else false
     */
    private boolean isExecute() {
        return commandLineArgs.hasOption(EXECUTE_OPT);
    }

    private String getExecute() {
        return commandLineArgs.getOptionValue(EXECUTE_OPT);
    }

    /**
     * Is disassemble option flag present?
     *
     * @return true id -d present in CLI args, else false
     */
    private boolean isDisassemble() {
        return commandLineArgs.hasOption(DISASSEMBLE_OPT);
    }

    private String getDisassemble() {
        return commandLineArgs.getOptionValue(DISASSEMBLE_OPT);
    }

    /**
     * Is print opcodes option flag present?
     *
     * @return true id -o present in CLI args, else false
     */
    private boolean isPrintOpCodes() {
        return commandLineArgs.hasOption(PRINT_OPCODES_OPT);
    }

    /**
     * Is interpret option flag present?
     *
     * @return true id -i present in CLI args, else false
     */
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

    private void disassembleCode(final String filename) throws IOException {
        final Disassembler disasm = new Disassembler();
        final String asm = disasm.disassamble(FileIo.newInputStream(filename));
        getIoStreams().println(asm);
    }

    private void executeByteCode(final String filename) throws IOException, ExecutionException {
        executeByteCode(new ByteCodeFile(FileIo.newInputStream(filename)));
    }

    private static String generateCompiledFileName(final String inFilename) {
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
                .append(NL)
                .append("--------------------")
                .append(NL);
        final String fmt = "%s0x%s%n";
        for (final OpCode op : OpCode.values()) {
            final StringBuilder mnemonic = new StringBuilder();
            mnemonic.append(StringUtils.rightPad(op.name().toLowerCase(LOCALE), MNEMONIC_PAD))
                    .append(' ');
            char c = 'A';
            for (int i = 0; i < op.getArgCount().getCount(); ++i) {
                mnemonic.append(c).append(' ');
                ++c;
            }
            buffer.append(String.format(fmt, StringUtils.rightPad(mnemonic.toString(), OPCODE_PAD), op.toHex()));
        }
        getIoStreams().print(buffer.toString());
    }

    private void interpretAssemblv(final String inFilename) throws IOException, AssemblerSyntaxException,
            ExecutionException {
        executeByteCode(assembleFile(inFilename));
    }

    private void executeByteCode(final ByteCodeFile bc) throws ExecutionException {
        if (!bc.isValid()) {
            throw new ExecutionException("Is not valid byte code!");
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

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

import com.google.common.collect.Lists;
import de.weltraumschaf.commons.IOStreams;
import de.weltraumschaf.commons.InvokableAdapter;
import de.weltraumschaf.commons.Version;
import de.weltraumschaf.registermachine.asm.Assembler;
import de.weltraumschaf.registermachine.asm.AssemblerSyntaxException;
import de.weltraumschaf.registermachine.bytecode.ByteCodeFile;
import de.weltraumschaf.registermachine.instructionset.Iadd;
import de.weltraumschaf.registermachine.instructionset.Iload;
import de.weltraumschaf.registermachine.instructionset.Instruction;
import de.weltraumschaf.registermachine.instructionset.Isasign;
import de.weltraumschaf.registermachine.instructionset.StdOut;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class App extends InvokableAdapter {

    private static final String EXECUTABLE = "machine";
    private static final String HEADER_FMT = "%n%nRegister based machine v%s%n%n";
    private static final String AUTHOR = "Sven Strittmatter <weltraumschaf@googlemail.com>";
    /**
     * URI to issue tracker.
     */
    private static final String ISSUE_TRACKER = "https://github.com/Weltraumschaf/registermachine/issues";
    /**
     * Usage footer.
     */
    private static final String FOOTER = String.format("%nWritten 2012 by %s%nWrite bugs to %s",
            AUTHOR, ISSUE_TRACKER);
    private static final Options OPTIONS = new Options();

    static {
        OPTIONS.addOption("h", false, "display help");
        OPTIONS.addOption("d", false, "display debug infos");
        OPTIONS.addOption("c", true, "compile assembly source");

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
        return commandLineArgs.hasOption("h");
    }

    private boolean isDebug() {
        return commandLineArgs.hasOption("d");
    }

    private boolean isCompile() {
        return commandLineArgs.hasOption("c");
    }

    private String getCompile() {
        return commandLineArgs.getOptionValue("c");
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
            InvokableAdapter.main(app, IOStreams.newDefault(), app.isDebug());
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
            compileAssemblyCode(getCompile());
        } else {
            executeByteCode();
        }
    }

    private void executeByteCode() throws IOException {
        /*
         * LOAD  #1
         * DIV   #2
         * MULT  #2
         * STORE #3
         * LOAD  #1
         * SUB   #3
         * STORE #3
         * END
         */
        final List<Instruction> instructions = Lists.newArrayList(
                new Isasign(0, 30),
                new Isasign(1, 6),
                new Iload(1, "#0"),
                new Iload(2, "#1"),
                new Iadd(0, 1, 2),
                new StdOut(0) //            new Idiv(3, 0, 2),
                //            new StdOut(3)
                //            new Imult(1),
                //            new Iload(1),
                //            new Isub(3),
                //            new Istore(3),
                );

//        opts = getopt('dp');
//        machine = new RegisterMachine(isset(opts['d']), isset(opts['p']));
        final RegisterMachine machine = new RegisterMachine(true, true);
        machine.setProgram(instructions);
        machine.getConfiguration().init();
        machine.run();
    }

    private void compileAssemblyCode(final String inFilename) throws IOException, AssemblerSyntaxException {
        getIoStreams().println(String.format("Compiling assembly file '%s' ...", inFilename));
        final Assembler asm = new Assembler();
        final ByteCodeFile bc = asm.assamble(new FileInputStream(new File(inFilename)));
        final String outFilename = inFilename.replace(".caythe", "") + ".ct";
        final FileOutputStream out = new FileOutputStream(new File(outFilename));
        IOUtils.write(bc.toArray(), out);
        IOUtils.closeQuietly(out);
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
}

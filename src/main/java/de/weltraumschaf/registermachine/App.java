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

import de.weltraumschaf.registermachine.instructionset.StdOut;
import de.weltraumschaf.registermachine.instructionset.Instruction;
import de.weltraumschaf.registermachine.instructionset.Isasign;
import de.weltraumschaf.registermachine.instructionset.Iadd;
import de.weltraumschaf.registermachine.instructionset.Iload;
import com.google.common.collect.Lists;
import de.weltraumschaf.commons.IOStreams;
import de.weltraumschaf.commons.InvokableAdapter;
import de.weltraumschaf.commons.Version;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class App extends InvokableAdapter {

    /**
     * Hide constructor because it is not intended to create objects of this type
     * outside of {@link App#main(de.weltraumschaf.commons.Invokable)}.
     *
     * @param args command line arguments
     */
    private App(final String[] args) {
        super(args);
    }

    /**
     * Main entry point for JVM.
     *
     * Catches all {@link Exception exception} and print their message to STDERR
     * and exits with -1.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        final App app = new App(args);
        boolean debug = false;

        try {
            if (args.length == 1 && "-d".equals(args[0])) {
                debug = true;
            }
            InvokableAdapter.main(app, IOStreams.newDefault(), debug);
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
        final Version version = new Version("/de/weltraumschaf/registermachine/version.properties");
        version.load();
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
            new StdOut(0)
//            new Idiv(3, 0, 2),
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
}

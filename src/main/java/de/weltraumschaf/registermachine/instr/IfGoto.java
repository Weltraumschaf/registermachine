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

package de.weltraumschaf.registermachine.instr;

import de.weltraumschaf.registermachine.vm.RuntimeConfiguration;

class IfGoto implements Instruction {

    private final int pos; // Sprungziel

    public  IfGoto(final int p) {
        pos = p;
    }

    // Befehl IF c[0]=0 GOTO pos ausfuehren
    @Override
    public void evaluate(final RuntimeConfiguration config) {
        throw new UnsupportedOperationException();
        // Inhalt des Akkumulators prüfen
//        if (config.getRegister(0) == 0) {
//        // Sprung ausfuehren
//            config.setInstructionCounter (pos - 1);
//        } else {
//        // sonst zum nuechsten Befehl
//            config.incInstructionCounter();
//        }
    }

    @Override
    public String toString() {
        return "IF c[0] = 0 GOTO " + pos;
    }

}

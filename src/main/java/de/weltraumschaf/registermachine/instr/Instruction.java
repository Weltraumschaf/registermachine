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

public interface Instruction {

    int REG_A = 0;
    int REG_B = 1;
    int REG_C = 2;

    void evaluate(RuntimeConfiguration config);

}
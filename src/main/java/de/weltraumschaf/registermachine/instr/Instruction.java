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

/**
 * Interface for machine instructions.
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
public interface Instruction {

    /**
     * Address of register A.
     *
     * Usually used as accumulator
     */
    int REG_A = 0;
    /**
     * Address of register B.
     */
    int REG_B = 1;
    /**
     * Address of register C.
     */
    int REG_C = 2;

    /**
     * Evaluates the instruction with given configuration.
     *
     * @param config runtime configuration
     */
    void evaluate(RuntimeConfiguration config);

}

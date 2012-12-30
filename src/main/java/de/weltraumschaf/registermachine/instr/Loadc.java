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

import de.weltraumschaf.registermachine.typing.Value;
import de.weltraumschaf.registermachine.vm.RuntimeConfiguration;

/**
 * Load constant instruction.
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
class Loadc implements Instruction {

    private final int register;
    private final int value;

    public  Loadc(final int register, final int value) {
        super();
        this.value = value;
        this.register = register;
    }

    @Override
    public void evaluate(final RuntimeConfiguration config) {
        config.setRegister(register, Value.valueOf(value));
    }

    @Override
    public String toString() {
        return String.format("loadc %d %d", register, value);
    }

}

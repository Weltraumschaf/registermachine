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

package de.weltraumschaf.registermachine.vm;

/**
 * Represents a scope.
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
class Scope {

    /**
     * Registers of current scope.
     */
    private final Register registers;
    /**
     * Constant values pool.
     */
    private final Pool constants;
    /**
     * Variable values pool.
     */
    private final Pool variables;
    /**
     * Functions defined in this scope.
     */
    private final FunctionTable functions; // NOPMD
    /**
     * Parent scope.
     *
     * Is null for the root scope of main function.
     */
    private final Scope parent;

    /**
     * Initialize scope with null as parent scope.
     */
    Scope() {
        this(null);
    }

    /**
     * Convenience constructor which initializes registers, pools and function tables.
     *
     * @param parent parent scope
     */
    Scope(final Scope parent) {
        this(parent, new Register(), Pools.newConstantPool(), Pools.newVariablePool(), new FunctionTable());
    }

    /**
     * Dedicated constructor.
     *
     * @param parent parent scope
     * @param registers used for instructions
     * @param constants constant pool
     * @param variables variable pool
     * @param functions function table
     */
    Scope(final Scope parent, final Register registers, final Pool constants, final Pool variables,
            final FunctionTable functions) {
        super();
        this.registers = registers;
        this.constants = constants;
        this.variables = variables;
        this.functions = functions;
        this.parent    = parent;
    }

    /**
     * Get registers.
     *
     * @return the registers
     */
    Register getRegisters() {
        return registers;
    }

    /**
     * Get the constant pool.
     *
     * @return the pool
     */
    Pool getConstants() {
        return constants;
    }

    /**
     * Get the variable pool.
     *
     * @return the pool
     */
    Pool getVariables() {
        return variables;
    }

    /**
     * Return the parent scope.
     *
     * @return may be null, if nit has no parent scope
     */
    Scope getParent() {
        return parent;
    }

}

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
package de.weltraumschaf.registermachine.asm;

import com.google.common.collect.Lists;
import de.weltraumschaf.registermachine.bytecode.OpCode;
import de.weltraumschaf.registermachine.typing.Code;
import de.weltraumschaf.registermachine.typing.Function;
import de.weltraumschaf.registermachine.typing.Value;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class LineParserTest {

    private final LineParser sut = new LineParser();

    @Test
    public void parse_emptyMainFunction() throws AssemblerSyntaxException {
        final List<String> src = Lists.newArrayList(".function 0 1 2 3");
        final Function main = sut.parseLines(src);
        assertThat(main.getNups(), is(0));
        assertThat(main.getNumparams(), is(1));
        assertThat(main.getIsVararg(), is(2));
        assertThat(main.getMaxStackSize(), is(3));
    }

    @Test
    public void parse_mainWithOneIntegerVariableFunction() throws AssemblerSyntaxException {
        final List<String> src = Lists.newArrayList(".function 0 1 2 3", ".var 40");
        final Function main = sut.parseLines(src);
        assertThat(main.getNups(), is(0));
        assertThat(main.getNumparams(), is(1));
        assertThat(main.getIsVararg(), is(2));
        assertThat(main.getMaxStackSize(), is(3));
        assertThat(main.getVariable(0), is(Value.valueOf(40)));
    }

    @Test
    public void parse_mainWithThreeIntegerVariableFunction() throws AssemblerSyntaxException {
        final List<String> src = Lists.newArrayList(".function 0 1 2 3", ".var 40", ".var 42", ".var 23");
        final Function main = sut.parseLines(src);
        assertThat(main.getNups(), is(0));
        assertThat(main.getNumparams(), is(1));
        assertThat(main.getIsVararg(), is(2));
        assertThat(main.getMaxStackSize(), is(3));
        assertThat(main.getVariable(0), is(Value.valueOf(40)));
        assertThat(main.getVariable(1), is(Value.valueOf(42)));
        assertThat(main.getVariable(2), is(Value.valueOf(23)));
    }

    @Test
    @Ignore
    public void parse_mainWithThreeVariablesFunction() throws AssemblerSyntaxException {
        final List<String> src = Lists.newArrayList(".function 0 1 2 3", ".var \"a\"", ".var \"b\"");
        final Function main = sut.parseLines(src);
        assertThat(main.getNups(), is(0));
        assertThat(main.getNumparams(), is(1));
        assertThat(main.getIsVararg(), is(2));
        assertThat(main.getMaxStackSize(), is(3));
    }

    @Test
    public void parse_mainWithOneIntegerConstantFunction() throws AssemblerSyntaxException {
        final List<String> src = Lists.newArrayList(".function 0 1 2 3", ".const 40");
        final Function main = sut.parseLines(src);
        assertThat(main.getNups(), is(0));
        assertThat(main.getNumparams(), is(1));
        assertThat(main.getIsVararg(), is(2));
        assertThat(main.getMaxStackSize(), is(3));
        assertThat(main.getConstant(0), is(Value.valueOf(40)));
    }

    @Test
    public void parse_mainWithThreeConstantVariableFunction() throws AssemblerSyntaxException {
        final List<String> src = Lists.newArrayList(".function 0 1 2 3", ".const 40", ".const 42", ".const 23");
        final Function main = sut.parseLines(src);
        assertThat(main.getNups(), is(0));
        assertThat(main.getNumparams(), is(1));
        assertThat(main.getIsVararg(), is(2));
        assertThat(main.getMaxStackSize(), is(3));
        assertThat(main.getConstant(0), is(Value.valueOf(40)));
        assertThat(main.getConstant(1), is(Value.valueOf(42)));
        assertThat(main.getConstant(2), is(Value.valueOf(23)));
    }

    private List<Integer> createArgsList(final int... inArgs) {
        final List<Integer> args = Lists.newArrayList();

        for (int i = 0; i < inArgs.length; ++i) {
            args.add(inArgs[i]);
        }

        return args;
    }

    @Test
    public void parse_mainWithCode() throws AssemblerSyntaxException {
        final List<String> src = Lists.newArrayList(".function 0 1 2 3",
                "loadc 0 1",
                "add 1 2 3",
                "return");
        final Function main = sut.parseLines(src);
        assertThat(main.getNups(), is(0));
        assertThat(main.getNumparams(), is(1));
        assertThat(main.getIsVararg(), is(2));
        assertThat(main.getMaxStackSize(), is(3));

        final List<Code> code = main.getCode();
        assertThat(code.size(), is(3));
        assertThat(code.get(0), is(new Code(OpCode.LOADC, createArgsList(0, 1))));
        assertThat(code.get(1), is(new Code(OpCode.ADD, createArgsList(1, 2, 3))));
        assertThat(code.get(2), is(new Code(OpCode.RETURN)));

    }
}

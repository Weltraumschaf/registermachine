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
import de.weltraumschaf.registermachine.inter.Value;
import java.util.List;
import java.util.Stack;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class LineParser {
    private static final int FUNCTION_ARG_COUNT = 4;
    private static final int FIRST_ARG = 0;
    private static final int SECOND_ARG = 1;
    private static final int THIRD_ARG = 2;
    private static final int FOURTH_ARG = 3;

    private final LineScanner scanner = new LineScanner();
    private final Stack<Function> functions = new Stack<Function>();
    private int currentLine;

    private void resetCurrentLine() {
        currentLine = 0;
    }

    private void incCurrentLine() {
        ++currentLine;
    }

    private int getCurrentLine() {
        return currentLine;
    }

    Function parseLines(final List<String> lines) throws AssemblerSyntaxException {
        resetCurrentLine();
        for (final String line : lines) {
            incCurrentLine();

            if (StringUtils.isBlank(line)) {
                continue;
            }

            final List<Token> tokens = scanner.parse(line);

            if (tokens.isEmpty()) {
                continue;
            }

            switch (tokens.get(0).getType()) {
                case METACODE:
                    parseMetaCode(tokens);
                    break;
                case OPCODE:
                    parseOpCode(tokens);
                    break;
                default:
                    throw new AssemblerSyntaxException(
                            String.format("First token of line neither mnemonic nor metacode!"), getCurrentLine());
            }
        }

        if (1 != functions.size()) {
            throw new AssemblerSyntaxException(
                    "There must be exactly one function on parse stack at end of parsen! Found " + functions.size());
        }

        return functions.pop();
    }

    private void parseMetaCode(final List<Token> tokens) throws AssemblerSyntaxException {
        final Token metaCode = tokens.get(0);

        if (".function".equals(metaCode.getValue())) {
            parseFunctionMetaCode(tokens.subList(1, tokens.size()));
        } else if (".const".equals(metaCode.getValue())) {
            parseConstantMetaCode(tokens.subList(1, tokens.size()));
        } else if (".var".equals(metaCode.getValue())) {
            parseVariableMetaCode(tokens.subList(1, tokens.size()));
        } else {
            throw new AssemblerSyntaxException(
                    String.format("Unsupported meta code mnemonic %s!", metaCode), getCurrentLine());
        }
    }

    private void parseFunctionMetaCode(final List<Token> tokens) throws AssemblerSyntaxException {
        if (tokens.size() != FUNCTION_ARG_COUNT) {
            throw new AssemblerSyntaxException(
                    String.format("Function mnemonic expects exactly %d parameters but was %d!",
                                  FUNCTION_ARG_COUNT, tokens.size()),
                    getCurrentLine());
        }

        for (final Token t : tokens) {
            if (t.getType() != TokenType.INTEGER) {
                throw new AssemblerSyntaxException("All four arguments of function mnemonic must be integers!",
                                                   getCurrentLine());
            }
        }

        functions.push(new Function(parseToByte(tokens.get(FIRST_ARG)),
                                    parseToByte(tokens.get(SECOND_ARG)),
                                    parseToByte(tokens.get(THIRD_ARG)),
                                    parseToByte(tokens.get(FOURTH_ARG))));
    }

    private static float parseToFloat(final Token t) {
        return Float.parseFloat(t.getValue());
    }
    private static byte parseToByte(final Token t) {
        return Byte.parseByte(t.getValue());
    }

    private void parseConstantMetaCode(final List<Token> tokens) throws AssemblerSyntaxException {
        if (tokens.size() != 1) {
            throw new AssemblerSyntaxException(
                    String.format("Constant mnemonic expects exactly 1 parameters! Given " + tokens.size()),
                    getCurrentLine());
        }

        final Token token = tokens.get(0);
        final Value value = determineValue(token);
        functions.peek().addConstant(value);
    }

    private void parseVariableMetaCode(final List<Token> tokens) throws AssemblerSyntaxException {
        if (tokens.size() != 1) {
            throw new AssemblerSyntaxException(
                    String.format("Variable mnemonic expects exactly 1 parameters! Given " + tokens.size()),
                    getCurrentLine());
        }

        final Token token = tokens.get(0);
        final Value value = determineValue(token);
        functions.peek().addVariable(value);
    }

    private void parseOpCode(final List<Token> tokens) throws AssemblerSyntaxException {
        final Token opCode = tokens.get(0);
        final OpCode op = OpCode.lokup(opCode.getValue());

        if (OpCode.UNKWONN == op) {
            throw new AssemblerSyntaxException(String.format("Unknown opcode mnemonic %s!", opCode.getValue()),
                                               getCurrentLine());
        }

        final List<Token> args = tokens.subList(1, tokens.size());

        if (args.size() != op.getArgCount().getCount()) {
            throw new AssemblerSyntaxException(String.format("Opcode %s requires %d arguments! %d arguments given.",
                                                             op.toString(), op.getArgCount().getCount(), args.size()),
                                               getCurrentLine());
        }

        final List<Integer> typedArgs = Lists.newArrayListWithCapacity(args.size());

        for (final Token arg : args) {
            if (TokenType.INTEGER != arg.getType()) {
                throw new AssemblerSyntaxException(
                        String.format("All arguments of opcode %s must be integers!", op.toString()),
                        getCurrentLine());
            }
            typedArgs.add(Integer.parseInt(arg.getValue()));
        }
        functions.peek().addCode(new Code(op, typedArgs));
    }

    private Value determineValue(final Token token) throws AssemblerSyntaxException {
        Value value;
        switch (token.getType()) {
            case INTEGER:
                value = Value.valueOf(parseToByte(token));
                break;
            case FLOAT:
                value = Value.valueOf(parseToFloat(token));
                break;
            case LITERAL:
                if ("nil".equals(token.getValue())) {
                    value = Value.getNil();
                } else if ("true".equals(token.getValue())) {
                    value = Value.getTrue();
                } else if ("false".equals(token.getValue())) {
                    value = Value.getFalse();
                } else {
                    throw new AssemblerSyntaxException(String.format("Unsupported literal %s!", token.getValue()),
                                                       getCurrentLine());
                }
                break;
            case STRING:
                value = Value.valueOf(token.getValue());
                break;
            default:
                throw new AssemblerSyntaxException(String.format("Unsupported constant type %s!", token.getType()),
                                                   getCurrentLine());

        }
        return value;
    }


}

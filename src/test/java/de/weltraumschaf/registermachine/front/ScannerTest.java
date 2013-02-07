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
package de.weltraumschaf.registermachine.front;

import de.weltraumschaf.commons.Null;
import de.weltraumschaf.commons.token.Token;
import de.weltraumschaf.commons.token.TokenType;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
public class ScannerTest {

    // CHECKSTYLE:OFF
    @Rule public ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON

    @Test
    public void invokeNextIfCurrentTokenIsNull() {
        final Scanner sut = Scanner.forString("");
        final Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanEmpty() {
        final Scanner sut = Scanner.forString("");
        sut.next();

        final Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanWhitespaces() {
        final Scanner sut = Scanner.forString("  \t   \n    ");
        sut.next();

        Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanEndofLine() {
        final Scanner sut = Scanner.forString("\n");
        sut.next();

        Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanEndofLineAfterLiteral() {
        final Scanner sut = Scanner.forString("foo\n");
        sut.next();

        Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.LITERAL));
        assertThat(((Token<String>) token).getValue(), is("foo"));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanWhitespacesWithEndofLine() {
        final Scanner sut = Scanner.forString("  \t   \n    ");
        sut.next();

        Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanSingleLineComment_withoutNewline() {
        final Scanner sut = Scanner.forString("   // this is a comment");
        sut.next();

        Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.COMMENT));
        assertThat(((Token<String>) token).getValue(), is("// this is a comment"));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanSingleLineComment_withNewline() {
        final Scanner sut = Scanner.forString("   // this is a comment\n");
        sut.next();

        Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.COMMENT));
        assertThat(((Token<String>) token).getValue(), is("// this is a comment"));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanSingleLineComment_withTwoNewlines() {
        final Scanner sut = Scanner.forString("   // this is a comment\n\n");
        sut.next();

        Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.COMMENT));
        assertThat(((Token<String>) token).getValue(), is("// this is a comment"));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanMultiLineComment_withoutNewline() {
        final Scanner sut = Scanner.forString("   /* this is a comment\nand one more line\n\nlast line */");
        sut.next();

        Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.COMMENT));
        assertThat(((Token<String>) token).getValue(), is("/* this is a comment\nand one more line\n\nlast line */"));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanMultiLineComment_withOneNewline() {
        final Scanner sut = Scanner.forString("   /* this is a comment\nand one more line\n\nlast line */\n");
        Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.COMMENT));
        assertThat(((Token<String>) token).getValue(), is("/* this is a comment\nand one more line\n\nlast line */"));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanMultiLineComment_withTwoNewline() {
        final Scanner sut = Scanner.forString("   /* this is a comment\nand one more line\n\nlast line */\n\n");
        Token token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.COMMENT));
        assertThat(((Token<String>) token).getValue(), is("/* this is a comment\nand one more line\n\nlast line */"));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(true));

        sut.next();
        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanMultiLineComment_throwSyntaxExceptionIfUnterminated() {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Unterminated multiline comment!");
        final Scanner sut = Scanner.forString("   /* this is a comment\nand one more line\n\nlast line ");
        sut.next();
    }

    @Test
    public void scanKeywords() {
        final Scanner sut = Scanner.forString(" var  const  "
                + "function  return  "
                + "for  "
                + "if  else  "
                + "switch  case  "
                + "break  continue  ");
        Token token = sut.getCurrentToken();

        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(((Token<String>) token).getValue(), is("var"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(((Token<String>) token).getValue(), is("const"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(((Token<String>) token).getValue(), is("function"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(((Token<String>) token).getValue(), is("return"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(((Token<String>) token).getValue(), is("for"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(((Token<String>) token).getValue(), is("if"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(((Token<String>) token).getValue(), is("else"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(((Token<String>) token).getValue(), is("switch"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(((Token<String>) token).getValue(), is("case"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(((Token<String>) token).getValue(), is("break"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.KEYWORD));
        assertThat(((Token<String>) token).getValue(), is("continue"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
        assertThat(sut.hasNext(), is(false));
    }

    @Test
    public void scanOperators() {
        final Scanner sut = Scanner.forString(" + - * / % ! && || = == != < > <= => :  ");
        Token token = sut.getCurrentToken();

        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("+"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("-"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("*"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("/"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("%"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("!"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("&&"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("||"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("="));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("=="));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("!="));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("<"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is(">"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("<="));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is("=>"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.OPERATOR));
        assertThat(((Token<String>) token).getValue(), is(":"));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
    }

    @Test
    public void scanIntegers() {
        final Scanner sut = Scanner.forString(" 42   23  ");
        Token token = sut.getCurrentToken();

        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(((Token<Integer>) token).getValue(), is(42));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.INTEGER));
        assertThat(((Token<Integer>) token).getValue(), is(23));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
    }

    @Test
    public void scanFloats() {
        final Scanner sut = Scanner.forString(" 3.14   2.272727  ");
        Token token = sut.getCurrentToken();

        assertThat(token.getType(), is(TokenType.FLOAT));
        assertThat(((Token<Float>) token).getValue(), is(3.14f));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.FLOAT));
        assertThat(((Token<Float>) token).getValue(), is(2.272727f));
        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
    }

    @Test
    public void scanString() {
        final Scanner sut = Scanner.forString(" \" this is a\nstring  \" ");
        Token token = sut.getCurrentToken();

        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(((Token<String>) token).getValue(), is(" this is a\nstring  "));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
    }

    @Test
    public void scanString_empty() {
        final Scanner sut = Scanner.forString(" \"\" ");
        Token token = sut.getCurrentToken();

        assertThat(token.getType(), is(TokenType.STRING));
        assertThat(((Token<String>) token).getValue(), is(""));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
    }

    @Test
    public void scanString_throwExceptionIfUnterminated() {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Unterminated string!");
        final Scanner sut = Scanner.forString(" \" this is");
        sut.getCurrentToken();
    }

    @Test @Ignore
    public void scanString_throwExceptionIfEmpty() {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Unterminated string!");
        final Scanner sut = Scanner.forString(" \"");
        sut.getCurrentToken();
    }

    @Test
    public void scanLiteral_boolean() {
        final Scanner sut = Scanner.forString("  true  false  ");
        Token token = sut.getCurrentToken();

        assertThat(token.getType(), is(TokenType.BOOLEAN));
        assertThat(((Token<Boolean>) token).getValue(), is(true));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.BOOLEAN));
        assertThat(((Token<Boolean>) token).getValue(), is(false));
        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
    }

    @Test
    public void scanLiteral_nil() {
        final Scanner sut = Scanner.forString("  nil  ");
        Token token = sut.getCurrentToken();

        assertThat(token.getType(), is(TokenType.NULL));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));

        assertThat(sut.hasNext(), is(true));
        sut.next();

        token = sut.getCurrentToken();
        assertThat(token.getType(), is(TokenType.EOF));
        assertThat(((Token<Null>) token).getValue(), is(Null.getInstance()));
    }

}

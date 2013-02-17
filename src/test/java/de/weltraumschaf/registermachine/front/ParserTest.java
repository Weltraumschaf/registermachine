/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.weltraumschaf.registermachine.front;

import de.weltraumschaf.commons.token.Tokens;
import de.weltraumschaf.registermachine.App;
import de.weltraumschaf.registermachine.ast.FunctionNode;
import de.weltraumschaf.registermachine.ast.SymbolNode;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
public class ParserTest {

    @Test
    public void determineTypedValue() {

    }

    @Test(expected = IllegalArgumentException.class)
    public void determineTypedValue_throwsExceptionOnNullToken() {
        Parser.determineTypedValue(null, "");
    }

    @Test
    public void isOperator() {
        assertThat(Parser.isOperator(Tokens.newOperatorToken("+"), "+"), is(true));
        assertThat(Parser.isOperator(Tokens.newOperatorToken("+"), "-"), is(false));
        assertThat(Parser.isOperator(Tokens.newOperatorToken("-"), "+"), is(false));
        assertThat(Parser.isOperator(Tokens.newOperatorToken("-"), "-"), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void isOperator_throesExcpetionOnNullToken() {
        Parser.isOperator(null, "+");
    }

    @Test(expected = IllegalArgumentException.class)
    public void isOperator_throesExcpetionOnNullLiterl() {
        Parser.isOperator(Tokens.newOperatorToken("+"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isOperator_throesExcpetionOnEmptyLiterl() {
        Parser.isOperator(Tokens.newOperatorToken("+"), "");
    }

    @Test
    public void parseEmptySource() {
        final Parser sut = Parser.forString("");
        sut.parse();

        final FunctionNode main = sut.getAbstractSyntaxtTree();
        assertThat(main.getName(), is(new SymbolNode("__main__")));
        assertThat(main.getStatements().isEmpty(), is(true));
    }

    @Test @Ignore
    public void parseExample() throws URISyntaxException, IOException {
        final URL input = getClass().getResource("example.caythe");
        final String source = FileUtils.readFileToString(new File(input.toURI()), App.ENCODING);
        final Parser sut = Parser.forString(source);

        sut.parse();
        assertThat(source, is(""));
    }

    @Test @Ignore
    public void parseSwitch() {

    }

    @Test @Ignore
    public void parseCountingFor() {

    }

    @Test @Ignore
    public void parseEndlessFor() {

    }

    @Test @Ignore
    public void parseConstitionalFor() {

    }

    @Test @Ignore
    public void parseFunctionwithNoArgVoid() {

    }

    @Test @Ignore
    public void parseFunctionWithNoArgAndOneReturn() {

    }

    @Test @Ignore
    public void parseFunctionWithNoArgAndMultiReturn() {

    }

    @Test @Ignore
    public void parseFunctionWithOneArgumentVoid() {

    }

    @Test @Ignore
    public void parseFunctionWithOneArgAndOneReturn() {

    }

    @Test @Ignore
    public void parseFunctionWithOneArgAndMultiReturn() {

    }

    @Test @Ignore
    public void parseFunctionWithMultiArgumentVoid() {

    }

    @Test @Ignore
    public void parseFunctionWithMultiArgAndOneReturn() {

    }

    @Test @Ignore
    public void parseFunctionWithMultiArgAndMultiReturn() {

    }

}

package test.java;

import main.java.EvaluatingException;
import main.java.Parser;
import main.java.ParserException;
import main.java.StringParserSource;
import main.java.element.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ParserTest {
	
	@Test
	public void testConstant() throws ParserException, EvaluatingException {
		testParseEval("8402766", 8402766);
		testParseEval("-27939", -27939);
		assertParseError("1j");
		assertParseError("475039563586365835634");
		assertParseError("-475039563586365835634");
	}
	
	@Test
	public void testIdentifier() throws ParserException, EvaluatingException {
		testParseEval("x", 5, Map.of("x", 5));
		testParseEval("xyz", -36438, Map.of("xyz", -36438, "x", 5));
	}
	
	@Test
	public void testBinaryExpression() throws ParserException, EvaluatingException {
		testParseEval("(1+3)", 4);
		testParseEval("(x/y)", 4, Map.of("x", -16, "y", -4));
		testParseEval("((x+3)*(y-4))", 32, Map.of("x", 5, "y", 8));
	}
	
	private void assertParseError(final String input) {
		try {
			parse(input);
			Assert.fail(input);
		} catch (final ParserException e) {
			// Ok
			System.err.println("Input: " + input);
			System.err.println(e.getMessage());
		}
	}
	
	private void testParseEval(final String input, final int evalResult) throws ParserException, EvaluatingException {
		testParseEval(input, evalResult, new HashMap<>());
	}
	
	private void testParseEval(final String input, final int evalResult, Map<String, Integer> vars) throws ParserException, EvaluatingException {
		Expression res = testParse(input);
		testEval(res, evalResult, vars);
	}
	
	private void testEval(Expression result, int evalResult, Map<String, Integer> vars) throws EvaluatingException {
		try {
			Assert.assertEquals(result + " with vars " + vars, evalResult, result.evaluate(vars));
		} catch (final EvaluatingException e) {
			System.err.format("Error while evaluating '%s'%n", result + " with vars " + vars);
			throw e;
		}
	}
	
	private Expression testParse(final String input) throws ParserException {
		try {
			Expression res = parse(input);
			Assert.assertEquals(input, input, res.toString());
			return res;
		} catch (final ParserException e) {
			System.err.format("Error while parsing '%s'%n", input);
			throw e;
		}
	}
	
	private Expression parse(final String input) throws ParserException {
		return new Parser(new StringParserSource(input)).parse();
	}
}

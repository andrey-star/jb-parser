package test.java;

import main.java.*;
import main.java.element.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllTests {
	
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
//		testParseEval("x", 5, List.of(5));
//		testParseEval("xyz", -36438, List.of(-36438, 5));
		assertParseError("xy;z");
	}
	
	@Test
	public void testBinaryExpression() throws ParserException, EvaluatingException {
		testParseEval("(1+3)", 4);
//		testParseEval("(x/y)", 4, List.of(-16, -4));
//		testParseEval("((x+3)*(y-4))", 32, List.of(5, 8));
	}
	
	@Test
	public void testIfExpression() throws ParserException, EvaluatingException {
		testParseEval("[(1>0)]?{5}:{6}", 5);
		testParseEval("[((1+2)>(3+4))]?{12}:{-10}", -10);
		testParseEval("[((10+20)>(20+10))]?{1}:{0}", 0);
//		testParseEval("[((x+y)=(y+x))]?{1}:{0}", 1, List.of(7, 10));
		assertParseError("[]?{}:{}");
		assertParseError("[1>]?{1}:{0}");
		assertParseError("[1>2]?{1}:{}");
	}
	
//	@Test
//	public void testFunction() throws ParserException {
//		testParseFunctionDefinition("f(x,y)={(g(x)+h(y))}");
//		testParseFunctionDefinition("f(x,y,z)={((g(x)+x)+h(z))}");
//		assertParseError("f(x,y a,z)={((g(x)+x)+h(z))}");
//		assertParseError("(x,y a,z)={((g(x)+x)+h(z))}");
//		assertParseError("fx,y a,z)={((g(x)+x)+h(z))}");
//		assertParseError("f(x,y a,z={((g(x)+x)+h(z))}");
//		assertParseError("f(x,y,z)=((g(x)+x)+h(z))}");
//		assertParseError("f(x,y,z)={((g(x)+x)+h(z))");
//	}
	
	private void assertParseError(final String input) {
		try {
			parseValue(input);
			Assert.fail(input);
		} catch (final ParserException e) {
			// Ok
			System.err.println("Input: " + input);
			System.err.println(e.getMessage());
		}
	}
	
	private void testParseEval(final String input, final int evalResult) throws ParserException, EvaluatingException {
		testParseEval(input, evalResult, new ArrayList<>(), new HashMap<>());
	}
	
	private void testParseEval(final String input, final int evalResult, List<Integer> vars, Map<String, Function> functions) throws ParserException, EvaluatingException {
		Expression res = testParseValue(input);
		testEval(res, evalResult, vars, functions);
	}
	
	private void testEval(Expression result, int evalResult, List<Integer> vars, Map<String, Function> functions) throws EvaluatingException {
		try {
			Assert.assertEquals(result + " with vars " + vars, evalResult, result.evaluate(vars, functions));
		} catch (final EvaluatingException e) {
			System.err.format("Error while evaluating '%s'%n", result + " with vars " + vars);
			throw e;
		}
	}
	
	private Expression testParseValue(final String input) throws ParserException {
		try {
			Expression res = parseValue(input);
			Assert.assertEquals(input, input, res.toString());
			return res;
		} catch (final ParserException e) {
			System.err.format("Error while parsing '%s'%n", input);
			throw e;
		}
	}
	
//	private Function testParseFunctionDefinition(final String input) throws ParserException {
//		try {
//			Function res = parseFunctionDefinition(input);
//			Assert.assertEquals(input, input, res.toString());
//			return res;
//		} catch (final ParserException e) {
//			System.err.format("Error while parsing '%s'%n", input);
//			throw e;
//		}
//	}
	
	private Expression parseValue(final String input) throws ParserException {
		return new Parser(new StringParserSource(input)).parseValue();
	}
	
//	private Function parseFunctionDefinition(final String input) throws ParserException {
//		return new Parser(new StringParserSource(input)).parseFunctionDefinition();
//	}
	
}

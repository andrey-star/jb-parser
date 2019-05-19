package test.java;

import main.java.elements.*;
import main.java.exceptions.EvaluatingException;
import main.java.exceptions.ParserException;
import main.java.parser.Parser;
import main.java.parser.StringParserSource;
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
	public void testIdentifier() throws ParserException {
		testParseValue("x");
		testParseValue("xy_fghss");
		assertParseError("xy;z");
	}
	
	@Test
	public void testBinaryExpression() throws ParserException, EvaluatingException {
		testParseEval("(1+3)", 4);
		testParseEval("(1/3)", 0);
		testParseEval("(45674>4545)", 1);
		testParseEval("(45674<4545)", 0);
		testParseEval("(1-3)", -2);
		assertParseError("1>3");
		assertParseError("(1|0)");
		assertParseError("(1 0)");
	}
	
	@Test
	public void testIfExpression() throws ParserException, EvaluatingException {
		testParseEval("[(1>0)]?{5}:{6}", 5);
		testParseEval("[((1+2)>(3+4))]?{12}:{-10}", -10);
		testParseEval("[((10+20)>(20+10))]?{1}:{0}", 0);
		testParseEval("[((7+8)=(8+7))]?{1}:{0}", 1);
		testParseEval("[((7+8)>(8+7))]?{1}:{0}", 0);
		assertParseError("[]?{}:{}");
		assertParseError("[1>]?{1}:{0}");
		assertParseError("[1>2]?{1}:{}");
	}
	
	@Test
	public void testFunctionDefinition() throws ParserException {
		testParseFunctionDefinition("f(x,y)={(g(x)+h(y))}");
		testParseFunctionDefinition("f(x,y,z)={((g(x)+x)+h(z))}");
		assertParseError("f(x,y a,z)={((g(x)+x)+h(z))}");
		assertParseError("(x,y a,z)={((g(x)+x)+h(z))}");
		assertParseError("fx,y a,z)={((g(x)+x)+h(z))}");
		assertParseError("f(x,y a,z={((g(x)+x)+h(z))}");
		assertParseError("f(x,y,z)=((g(x)+x)+h(z))}");
		assertParseError("f(x,y,z)={((g(x)+x)+h(z))");
	}
	
	@Test
	public void testCallExpression() throws ParserException, EvaluatingException {
		testParseEval(List.of("f(x,y)={(g(x)+h(y))}", "g(k)={h(((k+2)*3))}", "h(r)={(r-1)}", "f(2,3)"), 13);
		testParseEval(List.of("f(x,y)={(g(x)+h(y))}", "g(k)={h(((k+2)*3))}", "h(r)={(r-1)}", "f(2,3)"), 13);
		testParseEval(List.of("f(x,y)={(g(x)+h(y))}", "g(k)={h(((k+2)*3))}", "h(r)={(r-1)}", "f(2,3)"), 13);
		assertEvalError(List.of("f(x,y)={(g(x)+h(y))}", "g(k)={h(((k+2)*3))}", "h(r)={(r-1)}", "k(2,3)"), "FUNCTION NOT FOUND k:4");
	}
	
	@Test
	public void exceptionTests() throws ParserException {
		assertParseError("1 + 2 + 3 + 4 + 5");
		assertEvalError(List.of("f(x)={y}", "f(10)"), "PARAMETER NOT FOUND y:1");
		assertEvalError(List.of("g(x)={f(x)}", "g(10)"), "FUNCTION NOT FOUND f:1");
		assertEvalError(List.of("g(x)={(x+1)}", "g(10,20)"), "ARGUMENT NUMBER MISMATCH g:2");
		assertEvalError(List.of("g(a,b)={(a/b)}", "g(10,0)"), "RUNTIME ERROR (a/b):1");
		
	}
	
	private void assertEvalError(List<String> input, String error) throws ParserException {
		Map<String, Function> functions = parseFunctions(input);
		Expression exp = testParseValue(input.get(input.size() - 1), input.size() - 1);
		try {
			exp.evaluate(new ArrayList<>(), functions);
			Assert.fail(input.toString());
		} catch (Exception e) {
			System.err.println("Input: " + input);
			System.err.println(e.getMessage());
			Assert.assertEquals(e.getMessage(), error);
		}
	}
	
	private void testParseEval(String input, int evalResult) throws ParserException, EvaluatingException {
		testParseEval(List.of(input), evalResult);
	}
	
	private void testParseEval(List<String> input, int evalResult) throws ParserException, EvaluatingException {
		var functions = parseFunctions(input);
		Expression exp = testParseValue(input.get(input.size() - 1));
		try {
			int res = exp.evaluate(new ArrayList<>(), functions);
			Assert.assertEquals(res, evalResult);
		} catch (EvaluatingException e) {
			System.err.format("Error while evaluating '%s'%n", input);
			throw e;
		}
	}
	
	private Expression testParseValue(String input) throws ParserException {
		return testParseValue(input, 0);
	}
	
	private Expression testParseValue(String input, int line) throws ParserException {
		Parser p = new Parser(new StringParserSource(input));
		try {
			return p.parseValue(line);
		} catch (ParserException e) {
			System.err.format("Error while parsing '%s'%n", input);
			throw e;
		}
	}
	
	private Map<String, Function> parseFunctions(List<String> input) throws ParserException {
		Map<String, Function> functions = new HashMap<>();
		for (int i = 0; i < input.size() - 1; i++) {
			Function f = testParseFunctionDefinition(input.get(i));
			functions.put(f.getName(), f);
		}
		return functions;
	}
	
	private void assertParseError(String input) {
		assertParseError(List.of(input));
	}
	
	private void assertParseError(List<String> input) {
		for (String line : input) {
			try {
				parseValue(line);
				Assert.fail(line);
			} catch (ParserException e) {
				System.err.println("Input: " + line);
				System.err.println(e.getMessage());
				Assert.assertEquals(e.getMessage(), "SYNTAX ERROR");
			}
		}
	}
	
	private Function testParseFunctionDefinition(String input) throws ParserException {
		try {
			Function res = parseFunctionDefinition(input);
			Assert.assertEquals(input, input, res.toString());
			return res;
		} catch (ParserException e) {
			System.err.format("Error while parsing '%s'%n", input);
			throw e;
		}
	}
	
	private void parseValue(String input) throws ParserException {
		new Parser(new StringParserSource(input)).parseValue(0);
	}
	
	private Function parseFunctionDefinition(String input) throws ParserException {
		return new Parser(new StringParserSource(input)).parseFunctionDefinition(0);
	}
	
	
}

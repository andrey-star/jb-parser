package test.java;

import main.java.elements.Expression;
import main.java.elements.Function;
import main.java.elements.Program;
import main.java.exceptions.EvaluatingException;
import main.java.exceptions.ParserException;
import main.java.parser.Parser;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AllTests {
	
	@Test
	public void testConstant() throws ParserException, EvaluatingException {
		testParseEval(8402766, "8402766");
		testParseEval(-27939, "-27939");
		assertParseValueError("1j");
		assertParseValueError("475039563586365835634");
		assertParseValueError("-475039563586365835634");
	}
	
	@Test
	public void testIdentifier() throws ParserException {
		testParseValue("x");
		testParseValue("xy__fD_hss");
		assertEvalError("PARAMETER NOT FOUND b___G:1", "b___G");
		assertEvalError("PARAMETER NOT FOUND GH_FF:1", "GH_FF");
		assertEvalError("PARAMETER NOT FOUND x:1", "f(y)={x}", "f(1)");
		assertEvalError("PARAMETER NOT FOUND z:2", "g(y)={x}", "f(y)={g(z)}", "f(1)");
		assertParseValueError("xy;z");
		assertParseValueError("xy|z");
		assertParseValueError("xy,z");
		assertParseValueError("xy)z");
	}
	
	@Test
	public void testBinaryExpression() throws ParserException, EvaluatingException {
		testParseEval(4, "(1+3)");
		testParseEval(0, "(1/3)");
		testParseEval(1, "(45674>4545)");
		testParseEval(0, "(45674<4545)");
		testParseEval(-2, "(1-3)");
		assertEvalError("PARAMETER NOT FOUND GH_FF:1", "(GH_FF+2)");
		assertEvalError("PARAMETER NOT FOUND GH_FF:2", "f(x)={1}", "(GH_FF+klk)");
		assertEvalError("RUNTIME ERROR (1/0):1", "(1/0)");
		assertEvalError("RUNTIME ERROR (f(1)/f(0)):2", "f(x)={x}", "(f(1)/f(0))");
		assertEvalError("RUNTIME ERROR (f(x)/f(x)):1", "g(x)={(f(x)/f(x))}", "f(x)={x}", "g(0)");
		assertEvalError("RUNTIME ERROR (f(x)/f(x)):2", "f(x)={x}", "g(x)={(f(x)/f(x))}", "g(0)");
		assertEvalError("RUNTIME ERROR (a/b):1", "g(a,b)={(a/b)}", "g(10,0)");
		assertParseValueError("1>3");
		assertParseValueError("(1|0)");
		assertParseValueError("(1 0)");
		assertParseValueError("(1 0)88");
	}
	
	@Test
	public void testIfExpression() throws ParserException, EvaluatingException {
		testParseEval(5, "[(1>0)]?{5}:{6}");
		testParseEval(-10, "[((1+2)>(3+4))]?{12}:{-10}");
		testParseEval(0, "[((10+20)>(20+10))]?{1}:{0}");
		testParseEval(1, "[((7+8)=(8+7))]?{1}:{0}");
		testParseEval(0, "[((7+8)>(8+7))]?{1}:{0}");
		testParseEval(12, "[12]?{12}:{0}");
		testParseEval(3628800, "f(n)={[(n=0)]?{1}:{(f((n-1))*n)}}", "f(10)");
		testParseEval(-1, "f(f)={(f+1)}", "g(g)={(g-f(g))}",
				"z(x)={[f(x)]?{(f(x)+2)}:{g(x)}}", "z(((2/2)-2))");
		testParseEval(-1, "___f___(f)={(f+1)}", "___g___(g)={(g-___f___(g))}",
				"___z___(x)={[___f___(x)]?{(___f___(x)+2)}:{___g___(x)}}", "___z___(((2/2)-2))");
		assertEvalError("PARAMETER NOT FOUND GH_FF:1", "[(GH_FF+2)]?{1}:{0}");
		assertEvalError("RUNTIME ERROR (1/0):1", "[0]?{(1/1)}:{(1/0)}");
		assertEvalError("RUNTIME ERROR (y/0):2", "f(x)={x}", "g(y)={[f(y)]?{(f(y)-1)}:{(y/0)}}", "g(0)");
		assertEvalError("RUNTIME ERROR (f(y)/f(0)):3", "f(x)={x}", "g(h,b)={z((h+b))}",
				"z(y)={[f(y)]?{(f(y)-1)}:{(f(y)/f(0))}}", "g(-1,1)");
		assertParseValueError("[]?{}:{}");
		assertParseValueError("[1>]?{1}:{0}");
		assertParseValueError("[(xy)]?{1}:{0}");
		assertParseValueError("[(xy)+(yz)]?{1}:{0}");
		assertParseValueError("[1>2]?{1}:{}");
		assertParseValueError("[1>2]?{1}:{}88");
	}
	
	@Test
	public void testFunctionDefinition() throws ParserException {
		testParseFunction("f(x,y)={(g(x)+h(y))}");
		testParseFunction("f()={(1+2)}");
		testParseFunction("f(x,y,z)={((g(x)+x)+h(z))}");
		assertEvalError("DUPLICATE ARGUMENTS FOUND f:1", "f(x,x)={x}", "f(1,2)");
		assertEvalError("PARAMETER NOT FOUND z:1", "f(x,y)={z}", "f(1,2)");
		assertEvalError("ARGUMENT NUMBER MISMATCH f:2", "f(x,x)={x}", "f(1)");
		assertEvalError("ARGUMENT NUMBER MISMATCH f:1", "g(x)={f(x)}", "f(x,x)={x}", "g(1)");
		assertEvalError("PARAMETER NOT FOUND t:2", "g(x)={f(x,x)}", "f(x,y)={t}", "g(1)");
		assertEvalError("PARAMETER NOT FOUND t:1", "g(x)={f(t)}", "f(x,x)={x}", "g(1)");
		assertEvalError("ARGUMENT NUMBER MISMATCH f:2", "f(x,x)={x}", "g(x)={f(x)}", "g(1)");
		assertEvalError("PARAMETER NOT FOUND y:1", "f(x)={y}", "f(10)");
		assertEvalError("FUNCTION NOT FOUND f:1", "g(x)={f(x)}", "g(10)");
		assertParseFunctionError("g(h, b)={z((h+b))}");
		assertParseFunctionError("g(h,b)={z((h+b))}ghjh");
		assertParseFunctionError("g|(h,b)={z((h+b))}");
		assertParseFunctionError("f(x,y a,z)={((g(x)+x)+h(z))}");
		assertParseFunctionError("g(h, b)={z((h+b))}");
		assertParseFunctionError("(x,y a,z)={((g(x)+x)+h(z))}");
		assertParseFunctionError("fx,y a,z)={((g(x)+x)+h(z))}");
		assertParseFunctionError("f(x,y a,z={((g(x)+x)+h(z))}");
		assertParseFunctionError("f(x,y,z)=((g(x)+x)+h(z))}");
		assertParseFunctionError("f(x,y,z)={((g(x)+x)+h(z))");
	}
	
	@Test
	public void testCallExpression() throws ParserException, EvaluatingException {
		testParseEval(13, "f(x,y)={(g(x)+h(y))}", "g(k)={h(((k+2)*3))}", "h(r)={(r-1)}", "f(2,3)");
		testParseEval(13, "f(x,y)={(g(x)+h(y))}", "g(k)={h(((k+2)*3))}", "h(r)={(r-1)}", "f(2,3)");
		testParseEval(13, "f(x,y)={(g(x)+h(y))}", "g(k)={h(((k+2)*3))}", "h(r)={(r-1)}", "f(2,3)");
		testParseEval(3, "f()={(1+2)}","f()");
		assertEvalError("FUNCTION NOT FOUND k:4", "f(x,y)={(g(x)+h(y))}", "g(k)={h(((k+2)*3))}", "h(r)={(r-1)}", "k(2,3)");
		assertEvalError("ARGUMENT NUMBER MISMATCH g:2", "g(k,y)={h(((k+2)*3))}", "g(2)");
		assertEvalError("ARGUMENT NUMBER MISMATCH h:2", "h(x,y,z)={x}", "g(k)={h(((k+2)*3))}", "g(2)");
		assertEvalError("ARGUMENT NUMBER MISMATCH h:1", "g(k)={h(((k+2)*3))}", "h(x,y,z)={x}", "g(2)");
		assertEvalError("ARGUMENT NUMBER MISMATCH g:2", "g(x)={(x+1)}", "g(10,20)");
		assertParseValueError("1 + 2 + 3 + 4 + 5");
		assertParseValueError("g(1)67");
		
	}
	
	private void testParseEval(int evalResult, String... input) throws ParserException, EvaluatingException {
		Program program = parseProgram(input);
		try {
			int res = program.run();
			Assert.assertEquals(evalResult, res);
		} catch (EvaluatingException e) {
			System.err.format("Error while evaluating '%s'%n", Arrays.toString(input));
			throw e;
		}
	}
	
	private void assertEvalError(String error, String... input) throws ParserException {
		Program program = parseProgram(input);
		try {
			program.run();
			Assert.fail(Arrays.toString(input));
		} catch (Exception e) {
			System.err.println("Input: " + Arrays.toString(input));
			System.err.println(e.getMessage());
			Assert.assertEquals(error, e.getMessage());
		}
	}
	
	private void assertParseFunctionError(String input) {
		try {
			parseFunction(input);
			Assert.fail(input);
		} catch (ParserException e) {
			System.err.println("Input: " + input);
			System.err.println(e.getMessage());
			Assert.assertEquals(e.getMessage(), "SYNTAX ERROR");
		}
	}
	
	private void assertParseValueError(String input) {
		try {
			parseValue(input);
			Assert.fail(input);
		} catch (ParserException e) {
			System.err.println("Input: " + input);
			System.err.println(e.getMessage());
			Assert.assertEquals(e.getMessage(), "SYNTAX ERROR");
		}
	}
	
	private void testParseValue(String input) throws ParserException {
		try {
			Assert.assertEquals(input, input, parseValue(input).toString());
		} catch (ParserException e) {
			System.err.format("Error while parsing '%s'%n", input);
			throw e;
		}
	}
	
	private void testParseFunction(String input) throws ParserException {
		try {
			Assert.assertEquals(input, input, parseFunction(input).toString());
		} catch (ParserException e) {
			System.err.format("Error while parsing '%s'%n", input);
			throw e;
		}
	}
	
	private Expression parseValue(String expression) throws ParserException {
		return parseProgram(expression).getInit();
	}

	private Function parseFunction(String function) throws ParserException {
		Map<String, Function> functions = parseProgram(function, "0").getFunctions();
		List<Function> functionDefs = new ArrayList<>(functions.values());
		if (functionDefs.size() != 1) {
			throw new AssertionError();
		}
		return functionDefs.get(0);
	}
	
	private Program parseProgram(String... lines) throws ParserException {
		return new Parser().parseProgram(Arrays.asList(lines));
	}
	
	
}

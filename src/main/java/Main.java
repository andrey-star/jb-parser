package main.java;

import main.java.element.*;

import java.util.HashMap;
import java.util.List;

public class Main {
	
	public static void main(String[] args) throws ParserException, EvaluatingException {
		String s = "[((10+20)>(20+10))]?{1}:{0}";
		Function f = new Function("g", List.of("x", "y"), new BinaryExpression(new Variable("x"), new Variable("y"), new BinaryOperation("+")));
		System.out.println(f);
		Parser parser = new Parser(new StringParserSource(s));
		Expression exp = parser.parse();
		int res = exp.evaluate(new HashMap<>());
		System.out.println(res);
	}
	
}

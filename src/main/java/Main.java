package main.java;

import main.java.element.Expression;

import java.util.HashMap;

public class Main {
	
	public static void main(String[] args) throws ParserException, EvaluatingException {
		String s = "[((10+20)>(20+10))]?{1}:{0}";
		Parser parser = new Parser(new StringParserSource(s));
		Expression exp = parser.parse();
		int res = exp.evaluate(new HashMap<>());
		System.out.println(res);
	}
	
}

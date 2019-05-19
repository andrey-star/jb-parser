package main.java;

import main.java.element.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
	
	public static void main(String[] args) throws ParserException, EvaluatingException {
		String s = "f(x)={[(x>1)]?{(f((x-1))*x)}:{1}}\n"
				+ "f(10)\n"
				;
		BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(s.getBytes())));
		List<String> lines = in.lines().collect(Collectors.toList());
		Map<String, Function> functions = new HashMap<>();
		for (int i = 0; i < lines.size() - 1; i++) {
			Parser parser = new Parser(new StringParserSource(lines.get(i)));
			Function f = parser.parseFunctionDefinition();
			functions.put(f.getName(), f);
		}
		Parser parser = new Parser(new StringParserSource(lines.get(lines.size() - 1)));
		Expression exp = parser.parseValue();
		int res = exp.evaluate(new ArrayList<>(), functions);
		System.out.println(lines);
		System.out.println(res);
	}
	
}

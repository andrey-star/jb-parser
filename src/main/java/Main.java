package main.java;

import main.java.elements.*;
import main.java.exceptions.EvaluatingException;
import main.java.exceptions.ParserException;
import main.java.parser.Parser;
import main.java.parser.StringParserSource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Main {
	
	public static void main(String[] args) throws ParserException, EvaluatingException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("test.txt")));
		List<String> lines = in.lines().collect(Collectors.toList());
		in.close();
		Map<String, Function> functions = new HashMap<>();
		for (int i = 0; i < lines.size() - 1; i++) {
			Parser parser = new Parser(new StringParserSource(lines.get(i)));
			Function f = parser.parseFunctionDefinition(i);
			functions.put(f.getName(), f);
		}
		Parser parser = new Parser(new StringParserSource(lines.get(lines.size() - 1)));
		Expression exp = parser.parseValue(lines.size() - 1);
		int res = exp.evaluate(new ArrayList<>(), functions);
		System.out.println(lines);
		System.out.println(res);
	}
	
}

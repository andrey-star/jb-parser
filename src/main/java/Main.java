package main.java;

import main.java.elements.Program;
import main.java.exceptions.EvaluatingException;
import main.java.exceptions.ParserException;
import main.java.parser.Parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

class Main {
	
	public static void main(String[] args) throws ParserException, EvaluatingException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		List<String> lines = in.lines().collect(Collectors.toList());
		Parser parser = new Parser();
		Program program = parser.parseProgram(lines);
		int res = program.run();
		System.out.println(res);
	}
	
}

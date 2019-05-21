package main.java;

import main.java.elements.Program;
import main.java.exceptions.EvaluatingException;
import main.java.exceptions.ParserException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

class Main {
	
	public static void main(String[] args) throws ParserException, EvaluatingException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
		List<String> lines = in.lines().collect(Collectors.toList());
		Program program = new Program(lines);
		int res = program.run();
		System.out.println(res);
	}
	
}

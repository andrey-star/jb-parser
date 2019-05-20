package main.java;

import main.java.elements.*;
import main.java.exceptions.EvaluatingException;
import main.java.exceptions.ParserException;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

class Main {
	
	public static void main(String[] args) throws ParserException, EvaluatingException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("test.txt")));
		List<String> lines = in.lines().collect(Collectors.toList());
		Program program = new Program(lines);
		int res = program.run();
		System.out.println(lines);
		System.out.println(res);
	}
	
}

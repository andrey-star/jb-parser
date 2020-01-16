package main.java;

import main.java.element.Program;
import main.java.exception.EvaluatingException;
import main.java.exception.ParserException;
import main.java.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

class Main {
	
	public static void main(String[] args) {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
			List<String> lines = in.lines().collect(Collectors.toList());
			Parser parser = new Parser();
			Program program = parser.parseProgram(lines);
			int res = program.run();
			System.out.println(res);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserException | EvaluatingException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}

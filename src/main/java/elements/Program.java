package main.java.elements;

import main.java.exceptions.EvaluatingException;
import main.java.exceptions.ParserException;
import main.java.parser.Parser;
import main.java.parser.StringParserSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program {

	private final Map<String, Function> functions;
	private final Expression init;
	
	public Program(Map<String, Function> functions, Expression init) {
		this.functions = functions;
		this.init = init;
	}
	
	public Program(List<String> lines) throws ParserException {
		Map<String, Function> functions = new HashMap<>();
		for (int i = 0; i < lines.size() - 1; i++) {
			Parser parser = new Parser(new StringParserSource(lines.get(i)));
			Function f = parser.parseFunctionDefinition(i);
			functions.put(f.getName(), f);
		}
		this.functions = functions;
		Parser parser = new Parser(new StringParserSource(lines.get(lines.size() - 1)));
		init = parser.parseValue(lines.size() - 1);
	}
	
	public int run() throws EvaluatingException {
		return init.evaluate(new HashMap<>(), functions);
	}
}

package main.java.elements;

import main.java.exceptions.EvaluatingException;
import main.java.exceptions.ParserException;
import main.java.parser.Parser;
import main.java.parser.StringParserSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code Program} class represents a set of functions
 * and an expression. The run method evaluates the given expression,
 * using the specified functions.
 */
public class Program {
	
	private final Map<String, Function> functions;
	private final Expression init;
	
	public Program(Map<String, Function> functions, Expression init) {
		this.functions = functions;
		this.init = init;
	}
	
	/**
	 * Constructs a Program from a list of function definitions
	 * and an expression at the last place
	 * @param lines a list of function definitons and an expression
	 * @throws ParserException if the function definitions and/or the expression are malformed
	 */
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
	
	/**
	 * Evaluates the initial expression
	 * @return the result, produced by evaluating the expression
	 * @throws EvaluatingException if the expression and/or functions are defined incorrectly,
	 * or if an arithmetic error occurs
	 */
	public int run() throws EvaluatingException {
		return init.evaluate(new HashMap<>(), functions);
	}
}

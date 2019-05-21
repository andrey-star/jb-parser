package main.java.elements;

import main.java.exceptions.EvaluatingException;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Program} class represents a set of functions
 * and an expression. The run method evaluates the given expression,
 * using the specified functions.
 */
public class Program {
	
	private final Map<String, Function> functions;
	private final Expression init;
	
	/**
	 * Constructs a Program from a list of function definitions
	 * and an expression at the last position.
	 * @param functions a list of function definitions and an expression
	 * @param init the initial expression to be called on startup
	 */
	public Program(Map<String, Function> functions, Expression init) {
		this.functions = functions;
		this.init = init;
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
	
	// for testing purposes
	public Map<String, Function> getFunctions() {
		return functions;
	}
	// for testing purposes
	public Expression getInit() {
		return init;
	}
}

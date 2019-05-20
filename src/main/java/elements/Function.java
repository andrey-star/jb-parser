package main.java.elements;

import main.java.exceptions.DuplicateArgumentsException;
import main.java.exceptions.EvaluatingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code Function} class represents a function definition,
 * definde by the name of th function, the parameter names, and
 * an {@code Expression} representing the body of the function.
 */
public class Function {
	
	private final String name;
	private final List<String> params;
	private final Expression body;
	private final int line;
	
	/**
	 * @param name the name of the function
	 * @param params a list of parameter names
	 * @param body an {@code Expression} representing the body of the function
	 * @param line is the line, at which the function takes place
	 */
	public Function(String name, List<String> params, Expression body, int line) {
		this.name = name;
		this.params = params;
		this.body = body;
		this.line = line;
	}
	
	/**
	 * Calls the function with the given parameter values.
	 * @param argValues the values of the function parameters
	 * @param functions a list of available functions
	 * @return the result of calling the function
	 * @throws EvaluatingException if the the function or the the body are malformed,
	 * if the list of arguments is incorrect.
	 */
	int call(List<Integer> argValues, Map<String, Function> functions) throws EvaluatingException {
		Map<String, Integer> scope = new HashMap<>();
		for (int i = 0; i < argValues.size(); i++) {
			scope.put(params.get(i), argValues.get(i));
		}
		if (scope.size() != params.size()) {
			throw new DuplicateArgumentsException(name, line);
		}
		return body.evaluate(scope, functions);
	}
	
	public String getName() {
		return name;
	}
	
	List<String> getParams() {
		return params;
	}
	
	@Override
	public String toString() {
		return name + "(" + String.join(",", params) + ")={" + body + "}";
	}
}

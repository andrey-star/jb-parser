package main.java.elements;

import main.java.exceptions.EvaluatingException;

import java.util.Map;

/**
 * The {@code Evaluatable} interface should be implemented by any class,
 * whose instance can be evaluated.
 */
public interface Evaluatable {
	/**
	 * Evaluates the specified object.
	 * @param scope a list of available variables and their values
	 * @param functions a list of available functions
	 * @return the result of evaluating the given object.
	 * @throws EvaluatingException if the expression cannot be evaluated
	 * using the available variables and functions, or an arithmetic error occurs.
	 */
	int evaluate(Map<String, Integer> scope, Map<String, Function> functions) throws EvaluatingException;
	
}

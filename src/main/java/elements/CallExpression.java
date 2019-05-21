package main.java.elements;

import main.java.exceptions.ArgumentNumberMismatchException;
import main.java.exceptions.EvaluatingException;
import main.java.exceptions.FunctionNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The {@code CallExpression} class represents a call to a function,
 * with the given arguments.
 */
public class CallExpression extends Expression {
	
	private final String functionName;
	private final List<Expression> args;
	private final int line;
	
	/**
	 * Constructs a {@code CallExpression} class with the given function and arguments.
	 * @param functionName the name of function to be called
	 * @param args the arguments to be passed to the function
	 * @param line is the line, at which the function takes place
	 */
	public CallExpression(String functionName, List<Expression> args, int line) {
		this.functionName = functionName;
		this.args = args;
		this.line = line;
	}
	
	@Override
	public int evaluate(Map<String, Integer> scope, Map<String, Function> functions) throws EvaluatingException {
		List<Integer> evaluated = new ArrayList<>();
		for (Expression arg : args) {
			evaluated.add(arg.evaluate(scope, functions));
		}
		if (!functions.containsKey(functionName)) {
			throw new FunctionNotFoundException(functionName, line);
		}
		Function function = functions.get(functionName);
		if (args.size() != function.getParams().size()) {
			throw new ArgumentNumberMismatchException(functionName, line);
		}
		return functions.get(functionName).call(evaluated, functions);
	}
	
	@Override
	public String toString() {
		List<String> string = args.stream().map(Expression::toString).collect(Collectors.toList());
		return functionName + "(" + String.join(",", string) + ")";
	}
}
